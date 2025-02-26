package br.com.database_copier.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.database_copier.entities.Account;
import br.com.database_copier.entities.Address;
import br.com.database_copier.entities.Call;
import br.com.database_copier.entities.Channel;
import br.com.database_copier.entities.Company;
import br.com.database_copier.entities.Insurance;
import br.com.database_copier.entities.MassMessage;
import br.com.database_copier.entities.Notification;
import br.com.database_copier.entities.Profile;
import br.com.database_copier.entities.Provider;
import br.com.database_copier.enums.Role;
import br.com.neoapp.base.AbstractConverter;

public class ExecutePageUtil {

	public <T> void executePage(String[] fields, String sourceTable, String targetTable, Integer itensPerPage,
			Integer page, Integer totalPages, Session source, Class<T> entityType) {

		Boolean success = false;
		Session target = null;
		Transaction targetTransaction = null;
		ScrollableResults results = null;
		T entity = null;

		String query = GenericUtils.buildSql(fields, sourceTable, GenericUtils.SOURCE_SCHEMA, itensPerPage, page, "id");

		while (!success) {
			try {
				System.out.printf("BUSCANDO %s PAGINA: %d/%d%n", entityType.getSimpleName(), page + 1, totalPages);

				target = HibernateUtil.startSessionFactoryTargetDatabase().openSession();
				targetTransaction = target.beginTransaction();

				results = source.createNativeQuery(query).setTimeout(600000).setFetchSize(itensPerPage)
						.scroll(ScrollMode.FORWARD_ONLY);

				while (results.next()) {

					entity = new AbstractConverter<T>().convertJsonToEntity(entityType,
							GenericUtils.objectToJson(results.get(), fields));

					setDependencies(entity, entityType, source);

					target.saveOrUpdate(entity);
				}

				targetTransaction.commit();
				success = true;

			} catch (Exception e) {
				System.err.println("Erro ao processar a página: " + (page + 1));

				try {
					Thread.sleep(2000);
				} catch (InterruptedException interruptedException) {
					Thread.currentThread().interrupt();
				}
			} finally {
				if (target != null && target.isOpen()) {
					target.clear();
					target.close();
					source.clear();
				}
			}

		}

		results = null;
		query = null;
		targetTransaction = null;
		target = null;
		success = true;
		fields = null;
		sourceTable = null;
		targetTable = null;
		itensPerPage = null;
		page = null;
		totalPages = null;
		source = null;
		entityType = null;
		success = null;

	}

	public <T> void executePage(String[] sourceFields, String sourceTable, String targetTable, Integer itensPerPage,
			Integer page, Integer totalPages, Session source) {

		Boolean success = false;
		Session target = null;
		Transaction targetTransaction = null;
		ScrollableResults results = null;

		String query = GenericUtils.buildSql(sourceFields, sourceTable, GenericUtils.SOURCE_SCHEMA, itensPerPage, page,
				"notification_id");

		while (!success) {
			try {
				System.out.printf("BUSCANDO %s PAGINA: %d/%d%n", targetTable, page + 1, totalPages);

				target = HibernateUtil.startSessionFactoryTargetDatabase().openSession();
				targetTransaction = target.beginTransaction();

				results = source.createNativeQuery(query).setTimeout(600000).setFetchSize(itensPerPage)
						.scroll(ScrollMode.FORWARD_ONLY);

				while (results.next()) {

					Object[] object = results.get();

					StringBuilder findQuery = new StringBuilder();

					findQuery.append("SELECT TOP (1) [Notification_id], [related_id] FROM [dbo].[notificationRelated]");
					findQuery.append(" WHERE [Notification_id] = '");
					findQuery.append(object[0].toString());
					findQuery.append("' AND [related_id] = '");
					findQuery.append(object[1].toString());
					findQuery.append("';");

					Object uniqueResult = target.createNativeQuery(findQuery.toString()).uniqueResult();

					if (uniqueResult == null) {
						StringBuilder insertQuery = new StringBuilder();

						insertQuery.append(
								"INSERT INTO [dbo].[notificationRelated] ([Notification_id], [related_id]) VALUES ('");
						insertQuery.append(object[0].toString());
						insertQuery.append("', '");
						insertQuery.append(object[1].toString());
						insertQuery.append("');");

						target.createNativeQuery(insertQuery.toString()).executeUpdate();

						insertQuery = null;
					}

				}

				targetTransaction.commit();
				success = true;

			} catch (Exception e) {
				System.err.println("Erro ao processar a página: " + (page + 1));

				try {
					Thread.sleep(2000);
				} catch (InterruptedException interruptedException) {
					Thread.currentThread().interrupt();
				}
			} finally {
				if (target != null && target.isOpen()) {
					target.clear();
					target.close();
					source.clear();
				}
			}

		}

		results = null;
		query = null;
		targetTransaction = null;
		target = null;
		success = true;
		sourceFields = null;
		sourceTable = null;
		targetTable = null;
		itensPerPage = null;
		page = null;
		totalPages = null;
		source = null;
		success = null;

	}

	public <T> void setDependencies(T entity, final Class<T> entityType, Session source) {
		try {

			switch (entityType.getSimpleName()) {
			case "Profile": {

				Field idField = entityType.getDeclaredField("id");
				idField.setAccessible(true);
				String id = (String) idField.get(entity);
				Field field = entity.getClass().getDeclaredField("roles");
				field.setAccessible(true);

				List<Role> roles = new ArrayList<>();

				String query = "SELECT profile_id, role FROM " + GenericUtils.SOURCE_SCHEMA
						+ ".profiles_roles WHERE profile_id = '" + id + "'";

				ScrollableResults results = source.createNativeQuery(query).setTimeout(600000)
						.scroll(ScrollMode.FORWARD_ONLY);

				while (results.next()) {

					Object[] obj = results.get();

					if (obj[1] != null)
						roles.add(Role.valueOf(obj[1].toString()));

					obj = null;
				}

				field.set(entity, roles);

				idField = null;
				id = null;
				field = null;
				roles = null;
				query = null;
				source = null;
				results = null;

			}
				break;

			case "Channel": {

				Field idField = entityType.getDeclaredField("id");
				idField.setAccessible(true);
				String id = (String) idField.get(entity);
				Field field = entity.getClass().getDeclaredField("accounts");
				field.setAccessible(true);

				List<Account> accounts = new ArrayList<>();

				String query = "SELECT channel_id, account_id FROM " + GenericUtils.SOURCE_SCHEMA
						+ ".channel_account WHERE channel_id = '" + id + "'";

				ScrollableResults results = source.createNativeQuery(query).setTimeout(600000)
						.scroll(ScrollMode.FORWARD_ONLY);

				while (results.next()) {

					Object[] obj = results.get();

					if (obj[1] != null) {
						Account account = new Account();
						account.setId(obj[1].toString());
						accounts.add(account);
						account = null;
					}

					obj = null;
				}

				field.set(entity, accounts);

				idField = null;
				id = null;
				field = null;
				accounts = null;
				query = null;
				source = null;
				results = null;

			}
				break;

			case "News": {

				Field idField = entityType.getDeclaredField("id");
				idField.setAccessible(true);
				String id = (String) idField.get(entity);
				Field field = entity.getClass().getDeclaredField("segments");
				field.setAccessible(true);

				List<Company> segments = new ArrayList<>();

				String query = "SELECT news_id, segments_id FROM " + GenericUtils.SOURCE_SCHEMA
						+ ".news_segments WHERE news_id = '" + id + "'";

				ScrollableResults results = source.createNativeQuery(query).setTimeout(600000)
						.scroll(ScrollMode.FORWARD_ONLY);

				while (results.next()) {

					Object[] obj = results.get();

					if (obj[1] != null) {
						Company segment = new Company();
						segment.setId(obj[1].toString());
						segments.add(segment);
						segment = null;
					}

					obj = null;
				}

				field.set(entity, segments);

				idField = null;
				id = null;
				field = null;
				segments = null;
				query = null;
				source = null;
				results = null;

			}
				break;

			case "Account": {

				Field profileIdFie = entityType.getDeclaredField("profileId");
				profileIdFie.setAccessible(true);
				String profileId = (String) profileIdFie.get(entity);

				Field addressIdFie = entityType.getDeclaredField("addressId");
				addressIdFie.setAccessible(true);
				String addressId = (String) addressIdFie.get(entity);

				if (profileId != null) {
					Field field = entity.getClass().getDeclaredField("profile");
					field.setAccessible(true);

					Profile profile = new Profile();
					profile.setId(profileId);
					field.set(entity, profile);

					field = null;
					profile = null;
				}

				if (addressId != null) {
					Field field = entity.getClass().getDeclaredField("address");
					field.setAccessible(true);

					Address address = new Address();
					address.setId(addressId);
					field.set(entity, address);

					field = null;
					address = null;
				}

				profileIdFie = null;
				profileId = null;
				addressIdFie = null;
				addressId = null;

			}
				break;

			case "AccountCode": {

				Field accountIdFie = entityType.getDeclaredField("accountId");
				accountIdFie.setAccessible(true);
				String accountId = (String) accountIdFie.get(entity);

				if (accountId != null) {
					Field accountField = entity.getClass().getDeclaredField("account");
					accountField.setAccessible(true);

					Account account = new Account();
					account.setId(accountId);
					accountField.set(entity, account);

					accountField = null;
					account = null;
				}

				accountIdFie = null;
				accountId = null;
			}
				break;

			case "DataIntegration": {

				Field accountIdFie = entityType.getDeclaredField("accountId");
				accountIdFie.setAccessible(true);
				String accountId = (String) accountIdFie.get(entity);

				if (accountId != null) {
					Field accountField = entity.getClass().getDeclaredField("account");
					accountField.setAccessible(true);

					Account account = new Account();
					account.setId(accountId);
					accountField.set(entity, account);

					accountField = null;
					account = null;
				}

				accountIdFie = null;
				accountId = null;
			}
				break;

			case "Device": {

				Field accountIdFie = entityType.getDeclaredField("accountId");
				accountIdFie.setAccessible(true);
				String accountId = (String) accountIdFie.get(entity);

				if (accountId != null) {
					Field accountField = entity.getClass().getDeclaredField("account");
					accountField.setAccessible(true);

					Account account = new Account();
					account.setId(accountId);
					accountField.set(entity, account);

					accountField = null;
					account = null;
				}

				accountIdFie = null;
				accountId = null;
			}
				break;

			case "DeviceNotification": {

				Field accountIdFie = entityType.getDeclaredField("accountId");
				accountIdFie.setAccessible(true);
				String accountId = (String) accountIdFie.get(entity);

				if (accountId != null) {
					Field accountField = entity.getClass().getDeclaredField("account");
					accountField.setAccessible(true);

					Account account = new Account();
					account.setId(accountId);
					accountField.set(entity, account);

					accountField = null;
					account = null;
				}

				accountIdFie = null;
				accountId = null;
			}
				break;

			case "Evaluation": {

				Field accountIdFie = entityType.getDeclaredField("accountId");
				accountIdFie.setAccessible(true);
				String accountId = (String) accountIdFie.get(entity);

				if (accountId != null) {
					Field accountField = entity.getClass().getDeclaredField("account");
					accountField.setAccessible(true);

					Account account = new Account();
					account.setId(accountId);
					accountField.set(entity, account);

					accountField = null;
					account = null;
				}

				accountIdFie = null;
				accountId = null;
			}
				break;

			case "Hospitalization": {

				Field accountIdFie = entityType.getDeclaredField("accountId");
				accountIdFie.setAccessible(true);
				String accountId = (String) accountIdFie.get(entity);

				if (accountId != null) {
					Field accountField = entity.getClass().getDeclaredField("account");
					accountField.setAccessible(true);

					Account account = new Account();
					account.setId(accountId);
					accountField.set(entity, account);

					accountField = null;
					account = null;
				}

				accountIdFie = null;
				accountId = null;
			}
				break;

			case "Invitation": {

				Field callIdField = entityType.getDeclaredField("callId");
				callIdField.setAccessible(true);
				String callId = (String) callIdField.get(entity);

				Field guestField = entityType.getDeclaredField("guestId");
				guestField.setAccessible(true);
				String guestId = (String) guestField.get(entity);

				Field inviteSenderIdField = entityType.getDeclaredField("inviteSenderId");
				inviteSenderIdField.setAccessible(true);
				String inviteSenderId = (String) inviteSenderIdField.get(entity);

				if (callId != null) {
					Field field = entity.getClass().getDeclaredField("call");
					field.setAccessible(true);

					Call call = new Call();
					call.setId(callId);
					field.set(entity, call);

					field = null;
					call = null;
				}

				if (guestId != null) {
					Field field = entity.getClass().getDeclaredField("guest");
					field.setAccessible(true);

					Account account = new Account();
					account.setId(guestId);
					field.set(entity, account);

					field = null;
					account = null;
				}

				if (inviteSenderId != null) {
					Field field = entity.getClass().getDeclaredField("inviteSender");
					field.setAccessible(true);

					Account account = new Account();
					account.setId(inviteSenderId);
					field.set(entity, account);

					field = null;
					account = null;
				}

				guestField = null;
				guestId = null;
				inviteSenderIdField = null;
				inviteSenderId = null;
				callIdField = null;
				callId = null;

			}
				break;

			case "MassMessage": {

				Field senderIdField = entityType.getDeclaredField("senderId");
				senderIdField.setAccessible(true);
				String senderId = (String) senderIdField.get(entity);

				Field channelIdField = entityType.getDeclaredField("channelId");
				channelIdField.setAccessible(true);
				String channelId = (String) channelIdField.get(entity);

				if (senderId != null) {
					Field field = entity.getClass().getDeclaredField("sender");
					field.setAccessible(true);

					Account account = new Account();
					account.setId(senderId);
					field.set(entity, account);

					field = null;
					account = null;
				}

				if (channelId != null) {
					Field field = entity.getClass().getDeclaredField("channel");
					field.setAccessible(true);

					Channel channel = new Channel();
					channel.setId(channelId);
					field.set(entity, channel);

					field = null;
					channel = null;
				}

				senderIdField = null;
				senderId = null;
				channelIdField = null;
				channelId = null;

			}
				break;

			case "MedicalAppointment": {

				Field patientIdField = entityType.getDeclaredField("patientId");
				patientIdField.setAccessible(true);
				String patientId = (String) patientIdField.get(entity);

				Field callIdField = entityType.getDeclaredField("callId");
				callIdField.setAccessible(true);
				String callId = (String) callIdField.get(entity);

				Field providerIdField = entityType.getDeclaredField("providerId");
				providerIdField.setAccessible(true);
				String providerId = (String) providerIdField.get(entity);

				if (patientId != null) {
					Field field = entity.getClass().getDeclaredField("patient");
					field.setAccessible(true);

					Account account = new Account();
					account.setId(patientId);
					field.set(entity, account);

					field = null;
					account = null;
				}

				if (callId != null) {
					Field field = entity.getClass().getDeclaredField("call");
					field.setAccessible(true);

					Call call = new Call();
					call.setId(callId);
					field.set(entity, call);

					field = null;
					call = null;
				}

				if (providerId != null) {
					Field field = entity.getClass().getDeclaredField("provider");
					field.setAccessible(true);

					Provider provider = new Provider();
					provider.setId(providerId);
					field.set(entity, provider);

					field = null;
					provider = null;
				}

				patientIdField = null;
				patientId = null;
				callIdField = null;
				callId = null;
				providerIdField = null;
				providerId = null;

			}
				break;

			case "Message": {

				Field channelIdField = entityType.getDeclaredField("channelId");
				channelIdField.setAccessible(true);
				String channelId = (String) channelIdField.get(entity);

				Field senderIdField = entityType.getDeclaredField("senderId");
				senderIdField.setAccessible(true);
				String senderId = (String) senderIdField.get(entity);

				Field patientIdField = entityType.getDeclaredField("patientId");
				patientIdField.setAccessible(true);
				String patientId = (String) patientIdField.get(entity);

				Field massMessageIdField = entityType.getDeclaredField("massMessageId");
				massMessageIdField.setAccessible(true);
				String massMessageId = (String) massMessageIdField.get(entity);

				if (channelId != null) {
					Field field = entity.getClass().getDeclaredField("channel");
					field.setAccessible(true);

					Channel channel = new Channel();
					channel.setId(channelId);
					field.set(entity, channel);

					field = null;
					channel = null;
				}

				if (senderId != null) {
					Field field = entity.getClass().getDeclaredField("sender");
					field.setAccessible(true);

					Account sender = new Account();
					sender.setId(senderId);
					field.set(entity, sender);

					field = null;
					sender = null;
				}

				if (patientId != null) {
					Field field = entity.getClass().getDeclaredField("patient");
					field.setAccessible(true);

					Account patient = new Account();
					patient.setId(patientId);
					field.set(entity, patient);

					field = null;
					patient = null;
				}

				if (massMessageId != null) {
					Field field = entity.getClass().getDeclaredField("massMessage");
					field.setAccessible(true);

					MassMessage massMessage = new MassMessage();
					massMessage.setId(massMessageId);
					field.set(entity, massMessage);

					field = null;
					massMessage = null;
				}

				channelIdField = null;
				channelId = null;
				senderIdField = null;
				senderId = null;
				patientIdField = null;
				patientId = null;
				massMessageIdField = null;
				massMessageId = null;

			}
				break;

			case "MyHealth": {

				Field patientIdField = entityType.getDeclaredField("patientId");
				patientIdField.setAccessible(true);
				String patientId = (String) patientIdField.get(entity);

				if (patientId != null) {
					Field field = entity.getClass().getDeclaredField("patient");
					field.setAccessible(true);

					Account patient = new Account();
					patient.setId(patientId);
					field.set(entity, patient);

					field = null;
					patient = null;
				}

				patientIdField = null;
				patientId = null;

			}
				break;

			case "Card": {

				Field companyIdField = entityType.getDeclaredField("companyId");
				companyIdField.setAccessible(true);
				String companyId = (String) companyIdField.get(entity);

				Field accountIdField = entityType.getDeclaredField("accountId");
				accountIdField.setAccessible(true);
				String accountId = (String) accountIdField.get(entity);

				if (accountId != null) {
					Field accountField = entity.getClass().getDeclaredField("account");
					accountField.setAccessible(true);

					Account account = new Account();
					account.setId(accountId);
					accountField.set(entity, account);

					accountField = null;
					account = null;
				}

				if (companyId != null) {
					Field field = entity.getClass().getDeclaredField("company");
					field.setAccessible(true);

					Company company = new Company();
					company.setId(companyId);
					field.set(entity, company);

					field = null;
					company = null;
				}

				accountIdField = null;
				accountId = null;
				companyIdField = null;
				companyId = null;
			}
				break;

			case "Prescription": {

				Field accountIdField = entityType.getDeclaredField("accountId");
				accountIdField.setAccessible(true);
				String accountId = (String) accountIdField.get(entity);

				if (accountId != null) {
					Field field = entity.getClass().getDeclaredField("account");
					field.setAccessible(true);

					Account account = new Account();
					account.setId(accountId);
					field.set(entity, account);

					field = null;
					account = null;
				}

				accountIdField = null;
				accountId = null;

			}
				break;

			case "Provider": {

				Field addressIdField = entityType.getDeclaredField("addressId");
				addressIdField.setAccessible(true);
				String addressId = (String) addressIdField.get(entity);

				if (addressId != null) {
					Field field = entity.getClass().getDeclaredField("address");
					field.setAccessible(true);

					Address address = new Address();
					address.setId(addressId);
					field.set(entity, address);

					field = null;
					address = null;
				}

				addressIdField = null;
				addressId = null;

				Field idField = entityType.getDeclaredField("id");
				idField.setAccessible(true);
				String id = (String) idField.get(entity);
				Field field = entity.getClass().getDeclaredField("insurances");
				field.setAccessible(true);

				List<Insurance> insurances = new ArrayList<>();

				String query = "SELECT provider_id, insurances_id FROM " + GenericUtils.SOURCE_SCHEMA
						+ ".provider_insurance WHERE provider_id = '" + id + "'";

				ScrollableResults results = source.createNativeQuery(query).setTimeout(600000)
						.scroll(ScrollMode.FORWARD_ONLY);

				while (results.next()) {

					Object[] obj = results.get();

					if (obj[1] != null) {
						Insurance insurance = new Insurance();
						insurance.setId(obj[1].toString());
						insurances.add(insurance);
						insurance = null;
					}

					obj = null;
				}

				field.set(entity, insurances);

				idField = null;
				id = null;
				field = null;
				insurances = null;
				query = null;
				source = null;
				results = null;

			}
				break;

			case "Call": {

				Field channelIdField = entityType.getDeclaredField("channelId");
				channelIdField.setAccessible(true);
				String channelId = (String) channelIdField.get(entity);

				Field receiverIdField = entityType.getDeclaredField("receiverId");
				receiverIdField.setAccessible(true);
				String receiverId = (String) receiverIdField.get(entity);

				Field directReceiverIdField = entityType.getDeclaredField("directReceiverId");
				directReceiverIdField.setAccessible(true);
				String directReceiverId = (String) directReceiverIdField.get(entity);

				Field patientIdField = entityType.getDeclaredField("patientId");
				patientIdField.setAccessible(true);
				String patientId = (String) patientIdField.get(entity);

				if (directReceiverId != null) {
					Field field = entity.getClass().getDeclaredField("directReceiver");
					field.setAccessible(true);

					Account account = new Account();
					account.setId(directReceiverId);
					field.set(entity, account);

					field = null;
					account = null;
				}

				if (patientId != null) {
					Field field = entity.getClass().getDeclaredField("patient");
					field.setAccessible(true);

					Account account = new Account();
					account.setId(patientId);
					field.set(entity, account);

					field = null;
					account = null;
				}
				if (receiverId != null) {
					Field field = entity.getClass().getDeclaredField("receiver");
					field.setAccessible(true);

					Account account = new Account();
					account.setId(receiverId);
					field.set(entity, account);

					field = null;
					account = null;
				}

				if (channelId != null) {
					Field field = entity.getClass().getDeclaredField("channel");
					field.setAccessible(true);

					Channel channel = new Channel();
					channel.setId(channelId);
					field.set(entity, channel);

					field = null;
					channel = null;
				}

				directReceiverIdField = null;
				directReceiverId = null;
				receiverIdField = null;
				receiverId = null;
				patientIdField = null;
				patientId = null;
				channelIdField = null;
				channelId = null;

				Field idField = entityType.getDeclaredField("id");
				idField.setAccessible(true);
				String id = (String) idField.get(entity);
				Field field = entity.getClass().getDeclaredField("participants");
				field.setAccessible(true);

				List<Account> participants = new ArrayList<>();

				String query = "SELECT call_id, participants_id FROM " + GenericUtils.SOURCE_SCHEMA
						+ ".call_participants WHERE call_id = '" + id + "'";

				ScrollableResults results = source.createNativeQuery(query).setTimeout(600000)
						.scroll(ScrollMode.FORWARD_ONLY);

				while (results.next()) {

					Object[] obj = results.get();

					if (obj[1] != null) {
						Account account = new Account();
						account.setId(obj[1].toString());
						participants.add(account);
						account = null;
					}

					obj = null;
				}

				field.set(entity, participants);

				idField = null;
				id = null;
				field = null;
				participants = null;
				query = null;
				source = null;
				results = null;
			}
				break;

			case "Notification": {

				Field receiverIdField = entityType.getDeclaredField("receiverId");
				receiverIdField.setAccessible(true);
				String receiverId = (String) receiverIdField.get(entity);

				if (receiverId != null) {
					Field field = entity.getClass().getDeclaredField("receiver");
					field.setAccessible(true);

					Account account = new Account();
					account.setId(receiverId);
					field.set(entity, account);

					field = null;
					account = null;
				}

				receiverIdField = null;
				receiverId = null;
			}
				break;

			case "Related": {

				Field notificationIdField = entityType.getDeclaredField("notificationId");
				notificationIdField.setAccessible(true);
				String notificationId = (String) notificationIdField.get(entity);

				if (notificationId != null) {
					Field field = entity.getClass().getDeclaredField("notification");
					field.setAccessible(true);

					Notification notification = new Notification();
					notification.setId(notificationId);
					field.set(entity, notification);

					field = null;
					notification = null;
				}

				notificationIdField = null;
				notificationId = null;
			}
				break;

			case "RefreshToken": {

				Field accountIdField = entityType.getDeclaredField("accountId");
				accountIdField.setAccessible(true);
				String accountId = (String) accountIdField.get(entity);

				if (accountId != null) {
					Field field = entity.getClass().getDeclaredField("account");
					field.setAccessible(true);

					Account account = new Account();
					account.setId(accountId);
					field.set(entity, account);

					field = null;
					account = null;
				}

				accountIdField = null;
				accountId = null;
			}
				break;

			case "Review": {

				Field ratedByIdField = entityType.getDeclaredField("ratedById");
				ratedByIdField.setAccessible(true);
				String ratedById = (String) ratedByIdField.get(entity);

				Field providerIdField = entityType.getDeclaredField("providerId");
				providerIdField.setAccessible(true);
				String providerId = (String) providerIdField.get(entity);

				if (ratedById != null) {
					Field field = entity.getClass().getDeclaredField("ratedBy");
					field.setAccessible(true);

					Account account = new Account();
					account.setId(ratedById);
					field.set(entity, account);

					field = null;
					account = null;
				}

				if (providerId != null) {
					Field field = entity.getClass().getDeclaredField("provider");
					field.setAccessible(true);

					Provider provider = new Provider();
					provider.setId(providerId);
					field.set(entity, provider);

					field = null;
					provider = null;
				}

				ratedByIdField = null;
				ratedById = null;
				providerIdField = null;
				providerId = null;
			}
				break;

			case "Schedule": {

				Field professionalIdField = entityType.getDeclaredField("professionalId");
				professionalIdField.setAccessible(true);
				String professionalId = (String) professionalIdField.get(entity);

				Field patientIdField = entityType.getDeclaredField("patientId");
				patientIdField.setAccessible(true);
				String patientId = (String) patientIdField.get(entity);

				if (professionalId != null) {
					Field field = entity.getClass().getDeclaredField("professional");
					field.setAccessible(true);

					Account account = new Account();
					account.setId(professionalId);
					field.set(entity, account);

					field = null;
					account = null;
				}

				if (patientId != null) {
					Field field = entity.getClass().getDeclaredField("patient");
					field.setAccessible(true);

					Account patient = new Account();
					patient.setId(patientId);
					field.set(entity, patient);

					field = null;
					patient = null;
				}

				professionalIdField = null;
				professionalId = null;
				patientIdField = null;
				patientId = null;
			}
				break;

			case "WorkShift": {

				Field accountIdField = entityType.getDeclaredField("accountId");
				accountIdField.setAccessible(true);
				String accountId = (String) accountIdField.get(entity);

				if (accountId != null) {
					Field field = entity.getClass().getDeclaredField("account");
					field.setAccessible(true);

					Account account = new Account();
					account.setId(accountId);
					field.set(entity, account);

					field = null;
					account = null;
				}

				accountIdField = null;
				accountId = null;
			}
				break;

			default:
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

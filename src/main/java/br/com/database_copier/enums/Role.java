package br.com.database_copier.enums;

import lombok.Getter;

@Getter
public enum Role {
	
	  // CALLS
	  ROLE_VIEW_CALL_PAGE("Visualizar pagina de ligacoes", 1.0),
	  ROLE_RECEIVE_CALLS("Receber ligacoes", 1.1),
	  ROLE_RECEIVE_DIRECTED_CALLS("Receber ligacoes direcionadas", 1.2),
	  ROLE_CALL_TO_PATIENTS("Ligar para pacientes", 1.3),
      
	  //CHANNELS
	  ROLE_VIEW_CHANNELS("Visualizar canais", 2.0),
	  ROLE_CREATE_CHANNELS("Criar canais", 2.1),
	  ROLE_EDIT_CHANNELS("Editar canais", 2.2),
	  ROLE_DELETE_CHANNELS("Deletar canais", 2.3),
	  
	   // MASS MESSAGES
	  ROLE_VIEW_MASS_MESSAGES_PAGE("Visualizar pagina de mensagens em massa", 3.0),
	  ROLE_CREATE_MASS_MESSAGES("Criar mensagens em massa", 3.1),
	  
	   // MESSAGES
	  ROLE_VIEW_MESSAGES_PAGE("Visualizar pagina de mensagens", 4.0),
	  ROLE_VIEW_MESSAGES_RESPONSES_PAGE("Visualizar pagina de respostas das mensagens", 4.1),
	  ROLE_SEND_MESSAGES_TO_PATIENT("Enviar mensagens para paciente", 4.2),
	    
	   // SCHEDULE
	  ROLE_VIEW_SCHEDULE_PAGE("Visualizar a pagina de agenda", 5.0),
	  ROLE_CREATE_EVENTS_ON_YOUR_CALENDAR("Criar eventos em sua propria agenda", 5.1),
	  ROLE_VIEW_SCHEDULE_OF_OTHER_USERS("Ver a agenda de outros usuarios", 5.2),
	  ROLE_CREATE_EVENTS_ON_OTHER_USERS_CALENDAR("Criar eventos na agenda de outros usuarios", 5.3),
	  ROLE_EDIT_EVENTS_ON_YOUR_CALENDAR("Editar eventos em sua propria agenda", 5.4),
	  ROLE_EDIT_EVENTS_ON_OTHER_USERS_CALENDAR("Editar eventos na agenda de outros usuarios", 5.5),
	  ROLE_CANCEL_EVENTS_ON_YOUR_CALENDAR("Cancelar eventos em sua propria agenda", 5.6),
	  ROLE_CANCEL_EVENTS_ON_OTHER_USERS_CALENDAR("Cancelar eventos na agenda de outros usuarios", 5.7),
	    
	   // ACCOUNTS
	  ROLE_VIEW_ACCOUNTS_PAGE("Visualizar página de usuarios", 6.0),
	  ROLE_CREATE_ACCOUNTS("Criar usuarios", 6.1),
	  ROLE_EDIT_ACCOUNTS("Editar usuarios", 6.2),
	  ROLE_DELETE_ACCOUNTS("Deletar usuarios", 6.3),
      
	   // COMPANIES
	  ROLE_VIEW_COMPANIES_PAGE("Visualizar pagina de empresas", 7.0),
	  ROLE_CREATE_COMPANIES("Criar empresas", 7.1),
	  ROLE_EDIT_COMPANIES("Editar empresas", 7.2),
	  ROLE_DELETE_COMPANIES("Deletar empresas", 7.3),
      
	   // PATIENTS
	  ROLE_VIEW_PATIENTS_PAGE("Visualizar pagina de pacientes", 8.0),
	  ROLE_EXPORT_PATIENTS_DATA("Exportar informações de pacientes", 8.1),
      
	   // NEWS
	  ROLE_VIEW_NEWS_PAGE("Visualizar pagina de conteudos", 9.0),
	  ROLE_CREATE_NEWS("Criar conteudos", 9.1),
	  ROLE_EDIT_NEWS("Editar conteudos", 9.2),
	  ROLE_DELETE_NEWS("Deletar conteudos", 9.3),
      
	    // STATISTICS
	  ROLE_VIEW_STATISTICS_PAGE("Visualizar pagina de estatisticas", 10.0),
	
	  // PROFILES
	  ROLE_VIEW_PROFILES_PAGE("Visualizar pagina de perfis", 11.0),
	  ROLE_CREATE_PROFILES("Criar perfis", 11.1),
	  ROLE_EDIT_PROFILES("Editar perfis", 11.2),
	  ROLE_DELETE_PROFILES("Deletar perfis", 11.3);
	
	private String name;
	private Double code;

	Role(final String name, final Double code) {
		this.name = name;
		this.code = code;
	}

}

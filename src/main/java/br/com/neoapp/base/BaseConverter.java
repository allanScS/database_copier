/**
 * Base converter class to be implemented by the converters of the application.
 */
package br.com.neoapp.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author William Matos de Carvalho
 *
 */
public abstract class BaseConverter<E extends BaseEntity<?>, DTO> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6686583539363813903L;

	/**
	 * @param entities
	 * @return
	 */
	public List<DTO> convertToDTOList(final List<E> entities) {
		final List<DTO> dtos = new ArrayList<>();
		final Iterator<E> it = entities.iterator();
		while (it.hasNext()) {
			final E entity = it.next();
			if (entity != null && (entity.getDeleted() == null || entity.getDeleted() == false)) {
				dtos.add(convertToDTO(entity));
			}
		}

		return dtos;
	}

	/**
	 * @param dtos
	 * @return
	 */
	public List<E> convertToEntityList(final List<DTO> dtos) {
		final List<E> entities = new ArrayList<>();
		final Iterator<DTO> it = dtos.iterator();
		while (it.hasNext()) {
			final DTO dto = it.next();
			entities.add(convertToEntity(dto));
		}

		return entities;
	}

	/**
	 * @param entity
	 * @return
	 */
	public abstract DTO convertToDTO(final E entity);

	/**
	 * @param dto
	 * @return
	 */
	public abstract E convertToEntity(final DTO dto);
}

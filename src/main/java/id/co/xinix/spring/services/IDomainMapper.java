package id.co.xinix.spring.services;

public interface IDomainMapper<D, E, C> {
    E toEntity(D dto);

    D toDTO(E entity);

    D commandToDTO(C command);

    E commandToEntity(C command);

    D commandAndEntityToDTO(C command, E entity);

    E commandAndDtoToEntity(C command, D dto);
}

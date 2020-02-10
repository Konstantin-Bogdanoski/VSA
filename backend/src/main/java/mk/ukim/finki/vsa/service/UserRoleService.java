package mk.ukim.finki.vsa.service;

import mk.ukim.finki.vsa.model.base.UserRole;

/**
 * @author Natasha Stojanova
 */
public interface UserRoleService extends BaseEntityCrudService<UserRole> {
    public UserRole findByName(String name);
}

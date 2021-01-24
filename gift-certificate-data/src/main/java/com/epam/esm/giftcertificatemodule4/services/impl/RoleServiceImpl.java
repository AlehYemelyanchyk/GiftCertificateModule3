package com.epam.esm.giftcertificatemodule4.services.impl;

import com.epam.esm.giftcertificatemodule4.dao.RoleRepository;
import com.epam.esm.giftcertificatemodule4.entity.Role;
import com.epam.esm.giftcertificatemodule4.services.RoleService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> findAll(int page, int size) {
        page = Math.max(page, 0);
        size = Math.max(size, 5);
        Pageable paging = PageRequest.of(page, size);
        return roleRepository.findAll(paging).getContent();
    }

    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Transactional
    @Override
    public void save(Role object) {
        roleRepository.save(object);
    }

    @Override
    public void update(Role object) {
        roleRepository.save(object);
    }

    @Transactional
    @Override
    public void delete(Role object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(Long aLong) {
        throw new UnsupportedOperationException();
    }
}

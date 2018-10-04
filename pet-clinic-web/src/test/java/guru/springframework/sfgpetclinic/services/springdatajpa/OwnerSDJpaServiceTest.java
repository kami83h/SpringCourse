package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Create by Kami Hassanzadeh on 2018-10-04.
 */
@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {

    public static final String LAST_NAME = "Smith";

    @Mock
    OwnerRepository ownerRepository;

    @InjectMocks/* vid tar bort blir nullpointexception, den har @Service annotation*/
    OwnerSDJpaService service;

    Owner owner;

    @BeforeEach
    void setUp() {
        owner = Owner.builder().id(1L).lastName(LAST_NAME).build();
    }
    @Test
    void findByLastName() {
        when(ownerRepository.findByLastName(any())).thenReturn(owner);
        assertEquals(LAST_NAME, service.findByLastName(LAST_NAME).getLastName());
        verify(ownerRepository).findByLastName(any());
    }
    @Test
    void findAll() {
        Set<Owner> returnOwner = new HashSet<>();
        returnOwner.add(Owner.builder().id(1L).build());
        returnOwner.add(Owner.builder().id(2L).build());
        when(ownerRepository.findAll()).thenReturn(returnOwner);
        Set<Owner> owners = service.findAll();
        assertNotNull(owners);
        assertEquals(2,owners.size());
    }
    @Test
    void findById() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(owner));
        Owner owner = service.findById(1L);
        assertNotNull(owner);
    }
    @Test
    void findByIdNotFound() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());
        Owner owner = service.findById(1L);
        assertNull(owner);
    }
    @Test
    void save() {
        when(ownerRepository.save(any())).thenReturn(owner);
        assertNotNull(service.save(owner));
        verify(ownerRepository).save(any());
    }
    @Test
    void delete() {
        service.delete(owner);
        verify(ownerRepository).delete(any());
    }
    @Test
    void deleteById() {
        service.deleteById(1L);
        verify(ownerRepository).deleteById(anyLong());
    }
}
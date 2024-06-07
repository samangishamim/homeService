package service.addressService;

import base.service.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import model.Address;
import org.hibernate.SessionFactory;
import repository.addressRepository.AddressRepository;


@Slf4j
public class AddressServiceImpl extends BaseServiceImpl<Address, Long, AddressRepository> implements AddressService {

    public AddressServiceImpl(AddressRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }

}

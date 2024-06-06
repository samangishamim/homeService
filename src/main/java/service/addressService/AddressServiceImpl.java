package service.addressService;

import base.service.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import model.Address;
import org.hibernate.SessionFactory;
import repository.addressRepository.AddressRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
public class AddressServiceImpl extends BaseServiceImpl<Address, Long, AddressRepository> implements AddressService {

    public AddressServiceImpl(AddressRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }

    @Override
    public Optional<Address> findByProvinceAndCityAndDetail(String province, String city, String detail) {
        return repository.findByProvinceAndCityAndDetail(province, city, detail);
    }

    @Override

    public List<Address> findAllAddresses() {

        return repository.findAll();

    }


    @Override

    public Address getAddressById(Long id) {

        return findById(id);

    }


    @Override

    public Address saveAddress(Address address) {
        return saveOrUpdate(address);

    }


    @Override

    public void deleteAddress(Long id) {

        delete(id);

    }


    @Override

    public Address updateAddress(Address address) {

        return saveOrUpdate(address);

    }
}

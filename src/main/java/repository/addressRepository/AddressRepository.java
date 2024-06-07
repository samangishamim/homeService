package repository.addressRepository;

import base.repository.BaseRepository;
import model.Address;

import java.util.Optional;

public interface AddressRepository extends BaseRepository<Address, Long> {
    Optional<Address> findByProvinceAndCityAndDetail(String province, String city, String detail);


}

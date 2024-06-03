package service.addressService;

import base.service.BaseService;
import model.Address;

import java.util.List;
import java.util.Optional;

public interface AddressService  extends BaseService<Address,Long> {
     Optional<Address> findByProvinceAndCityAndDetail(String province, String city, String detail);
     List<Address> findAllAddresses();


     Address getAddressById(Long id);


     Address saveAddress(Address address);


     void deleteAddress(Long id);


     Address updateAddress(Address address);
}

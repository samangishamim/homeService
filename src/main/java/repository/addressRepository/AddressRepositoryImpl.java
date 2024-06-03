package repository.addressRepository;

import base.repository.BaseRepositoryImpl;
import model.Address;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class AddressRepositoryImpl extends BaseRepositoryImpl<Address, Long> implements AddressRepository {
    public AddressRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Address> getEntityClass() {
        return Address.class;
    }

    @Override
    public String getMyClass() {
        return "address";
    }

    @Override
    public Optional<Address> findByProvinceAndCityAndDetail(String province, String city, String detail) {
        Session session = sessionFactory.getCurrentSession();
        Query<Address> query = session.createQuery("FROM Address a WHERE a.province = :province AND a.city = :city AND a.detail = :detail", Address.class);
        query.setParameter("province", province);
        query.setParameter("city", city);
        query.setParameter("detail", detail);

        List<Address> addressList = query.getResultList();
        if (addressList.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(addressList.get(0));
        }
    }
}

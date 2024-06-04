package utility;

import connection.SessionFactorySingleton;
import org.hibernate.SessionFactory;
import org.hibernate.metamodel.internal.AbstractDynamicMapInstantiator;
import repository.addressRepository.AddressRepository;
import repository.addressRepository.AddressRepositoryImpl;
import repository.adminRepository.AdminRepository;
import repository.adminRepository.AdminRepositoryImpl;
import repository.commentRepository.CommentRepository;
import repository.commentRepository.CommentRepositoryImpl;
import repository.customerRepository.CustomerRepository;
import repository.customerRepository.CustomerRepositoryImpl;
import repository.orderRepository.OrderRepository;
import repository.orderRepository.OrderRepositoryImpl;
import repository.proposalRepository.ProposalRepository;
import repository.proposalRepository.ProposalRepositoryImpl;
import repository.serviceRepository.ServiceRepository;
import repository.serviceRepository.ServiceRepositoryImpl;
import repository.specialistRepository.SpecialistRepository;
import repository.specialistRepository.SpecialistRepositoryImpl;
import repository.subserviceRepository.SubServiceRepository;
import repository.subserviceRepository.SubServiceRepositoryImpl;
import service.addressService.AddressService;
import service.addressService.AddressServiceImpl;
import service.adminService.AdminService;
import service.adminService.AdminServiceImpl;
import service.commentService.CommentService;
import service.commentService.CommentServiceImpl;
import service.customerService.CustomerService;
import service.customerService.CustomerServiceImpl;
import service.orderService.OrderService;
import service.orderService.OrderServiceImpl;
import service.proposalService.ProposalService;
import service.proposalService.ProposalServiceImpl;
import service.serviceService.ServiceService;
import service.serviceService.ServiceServiceImpl;
import service.specialistService.SpecialistService;
import service.specialistService.SpecialistServiceImpl;
import service.subserviceService.SubServiceService;
import service.subserviceService.SubServiceServiceImpl;


public class ApplicationContext {
    private final SessionFactorySingleton sessionFactorySingleton;

    public ApplicationContext(SessionFactorySingleton sessionFactorySingleton) {
        this.sessionFactorySingleton = sessionFactorySingleton;
    }

    private final static SessionFactory SESSION_FACTORY;
    private final static AddressRepository ADDRESS_REPOSITORY;
    private final static AddressService ADDRESS_SERVICE;


    private final static AdminRepository ADMIN_REPOSITORY;
    private final static AdminService ADMIN_SERVICE;


    private final static CommentRepository COMMENT_REPOSITORY;
    private final static CommentService COMMENT_SERVICE;


    private final static CustomerRepository CUSTOMER_REPOSITORY;
    private final static CustomerService CUSTOMER_SERVICE;


    private final static OrderRepository ORDER_REPOSITORY;
    private final static OrderService ORDER_SERVICE;


    private final static ProposalRepository PROPOSAL_REPOSITORY;
    private final static ProposalService PROPOSAL_SERVICE;

    private final static ServiceRepository SERVICE_REPOSITORY;
    private final static ServiceService SERVICE_SERVICE;
    private final static SpecialistRepository SPECIALIST_REPOSITORY;
    private final static SpecialistService SPECIALIST_SERVICE;

    private final static SubServiceRepository SUB_SERVICE_REPOSITORY;
    private final static SubServiceService SUB_SERVICE_SERVICE;

    static {
        SESSION_FACTORY = SessionFactorySingleton.getInstance();


        ADDRESS_REPOSITORY = new AddressRepositoryImpl(SESSION_FACTORY);
        ADDRESS_SERVICE = new AddressServiceImpl(ADDRESS_REPOSITORY, SESSION_FACTORY);

        ADMIN_REPOSITORY = new AdminRepositoryImpl(SESSION_FACTORY);
        ADMIN_SERVICE = new AdminServiceImpl(ADMIN_REPOSITORY, SESSION_FACTORY);

        COMMENT_REPOSITORY = new CommentRepositoryImpl(SESSION_FACTORY);
        COMMENT_SERVICE = new CommentServiceImpl(COMMENT_REPOSITORY, SESSION_FACTORY);

        CUSTOMER_REPOSITORY = new CustomerRepositoryImpl(SESSION_FACTORY);
        CUSTOMER_SERVICE = new CustomerServiceImpl(CUSTOMER_REPOSITORY, SESSION_FACTORY);

        ORDER_REPOSITORY = new OrderRepositoryImpl(SESSION_FACTORY);
        ORDER_SERVICE = new OrderServiceImpl(ORDER_REPOSITORY, SESSION_FACTORY);

        PROPOSAL_REPOSITORY = new ProposalRepositoryImpl(SESSION_FACTORY);
        PROPOSAL_SERVICE = new ProposalServiceImpl(PROPOSAL_REPOSITORY, SESSION_FACTORY);

        SERVICE_REPOSITORY = new ServiceRepositoryImpl(SESSION_FACTORY);
        SERVICE_SERVICE = new ServiceServiceImpl(SERVICE_REPOSITORY, SESSION_FACTORY);

        SPECIALIST_REPOSITORY = new SpecialistRepositoryImpl(SESSION_FACTORY);
        SPECIALIST_SERVICE = new SpecialistServiceImpl(SPECIALIST_REPOSITORY, SESSION_FACTORY);

        SUB_SERVICE_REPOSITORY = new SubServiceRepositoryImpl(SESSION_FACTORY);
        SUB_SERVICE_SERVICE = new SubServiceServiceImpl(SUB_SERVICE_REPOSITORY, SESSION_FACTORY);

    }

    public static AddressService getAddressService() {
        return ADDRESS_SERVICE;
    }

    public static AdminService getAdminService() {
        return ADMIN_SERVICE;
    }

    public static CommentService getCommentService() {
        return COMMENT_SERVICE;
    }

    public static CustomerService getCustomerService() {
        return CUSTOMER_SERVICE;
    }

    public static OrderService getOrderService() {
        return ORDER_SERVICE;
    }

    public static ProposalService getProposalService() {
        return PROPOSAL_SERVICE;
    }

    public static ServiceService getServiceService() {
        return SERVICE_SERVICE;
    }

    public static SpecialistService getSpecialistService() {
        return SPECIALIST_SERVICE;
    }

    public static SubServiceService getSubServiceService() {
        return SUB_SERVICE_SERVICE;
    }
}

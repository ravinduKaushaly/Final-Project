package lk.ijse.finalProject.bo.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.finalProject.bo.custom.CustomerBO;
import lk.ijse.finalProject.dao.DAOFactory;
import lk.ijse.finalProject.dao.SuperDAO;
import lk.ijse.finalProject.dao.custom.CustomerDAO;
import lk.ijse.finalProject.dto.CustomerDTO;
import lk.ijse.finalProject.entity.CustomerEntity;
import lk.ijse.finalProject.utill.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerBOImpl implements CustomerBO {

    private final CustomerDAO cusDAO = (CustomerDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    public CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException {
        CustomerEntity search = cusDAO.search(id);
        return new CustomerDTO(search.getId(),search.getName(),search.getAddress(),search.getContact(),search.getEmail());
    }

    public boolean updateCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        return cusDAO.update(new CustomerEntity(customerDTO.getId(), customerDTO.getName(), customerDTO.getAddress(),
                customerDTO.getContact(), customerDTO.getEmail()));
    }

    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return cusDAO.delete(id);
    }

    public ObservableList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ObservableList<CustomerDTO> list = FXCollections.observableArrayList();
        ObservableList<CustomerEntity> customerEntities = cusDAO.searchAllCustomer();
        for (CustomerEntity customerEntity : customerEntities) {
            list.add(new CustomerDTO(customerEntity.getId(),customerEntity.getName(),customerEntity.getAddress(),
                    customerEntity.getContact(),customerEntity.getEmail()));
        }
        return list;
    }

    public static boolean addCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO customer VALUES (?,?,?,?,?)";
        return CrudUtil.execute(sql, customerDTO.getId(), customerDTO.getName(), customerDTO.getAddress(), customerDTO.getContact(), customerDTO.getEmail());
    }
}

package by.epamtc.paymentservice.service;

import by.epamtc.paymentservice.bean.Organization;
import by.epamtc.paymentservice.service.exception.ServiceException;

import java.util.List;

public interface OrganizationService {

    List<Organization> getOrgList() throws ServiceException;
    List<Organization> getOrgList(String name) throws ServiceException;
    List<Organization> getActiveOrgList() throws ServiceException;
    List<Organization> getActiveOrgList(String name) throws ServiceException;
    Organization getOrg(int id) throws ServiceException;
    void addOrganization(String name, int accountID) throws ServiceException;
    void editOrganization(int id, String name, int accountID) throws ServiceException;
    void setStatus(int id, int status) throws ServiceException;


}

package com.gabriels.school.service;

import com.gabriels.school.SchoolConstants;
import com.gabriels.school.model.Contact;
import com.gabriels.school.repository.ContactRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import java.util.List;
import java.util.Optional;

@Service
@RequestScope
@Slf4j
@Data
public class ContactService {

    public ContactService() {
        log.info("ContactService instance created: ");
    }

    @Autowired
    private ContactRepository contactRepository;

    public boolean saveMessageDetails(Contact contact) {
        contact.setStatus(SchoolConstants.OPEN);
        Contact result = contactRepository.save(contact);
        return result.getContactId() > 0;
    }

    public List<Contact> findMessagesWithOpenStatus() {
        return contactRepository.findByStatus(SchoolConstants.OPEN);
    }

    public boolean updateMessage(int contactId) {
        Optional<Contact> contact = contactRepository.findById(contactId);
        contact.ifPresent(contact1 -> {
            contact1.setStatus(SchoolConstants.CLOSED);
        });
        Contact updatedContact = contactRepository.save(contact.get());
        if (updatedContact.getUpdatedBy() != null) {
            log.info("Contact message with id: {} closed", contactId);
            return true;
        }
        return false;
    }
}

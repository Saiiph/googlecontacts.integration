package com.leones.googlecontacts.integration.controller;

import java.io.IOException;
import java.util.Map;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.leones.googlecontacts.integration.service.GoogleContactsService;

@RestController
@RequestMapping("/api/contacts")
public class GoogleContactsController {

    private final GoogleContactsService googleContactsService;

    public GoogleContactsController(GoogleContactsService googleContactsService) {
        this.googleContactsService = googleContactsService;
    }

    @GetMapping
    public String getAllContacts(@AuthenticationPrincipal OAuth2User principal) {
        return googleContactsService.getContacts(principal.getName());
    }

    @PostMapping
    public String createContact(@AuthenticationPrincipal OAuth2User principal, @RequestBody Map<String, Object> contactData) {
        return googleContactsService.createContact(principal.getName(), contactData);
    }

    @PostMapping("/update")
    public String updateContact(
            @RequestParam String resourceName,
            @RequestParam String familyName,
            @RequestParam(required = false) List<String> emails,
            @RequestParam(required = false) List<String> phones) {

        try {
            googleContactsService.updateContact(resourceName, familyName, emails, phones);
            System.out.println("Contact updated: " + resourceName);
            return "Contact updated successfully";
        } catch (IOException e) {
            return "error";
        }
    }

    @PostMapping("/delete")
    public String deleteContact(@RequestParam String resourceName) {
        try {
            googleContactsService.deleteContact(resourceName);
            System.out.println("Deleted contact: " + resourceName);
            return "Contact deleted successfully";
        } catch (IOException e) {
            return "error";
        }
    }
}

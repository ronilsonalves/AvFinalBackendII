package com.ronilsonalves.msusers.repository;

import com.ronilsonalves.msusers.model.User;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class KeyCloakRepository implements IUserRepository {

    private static Logger log = Logger.getLogger(KeyCloakRepository.class.getName());

    private final Keycloak keycloak;

    @Value("${dh.keycloak.realm}")
    private String realm;

    @Override
    public User findUserById(String id) throws NotFoundException {
        try {
            UserResource userResource = keycloak
                    .realm(realm)
                    .users().get(id);

            UserRepresentation userRepresentation = userResource.toRepresentation();
            return fromRepresentation(userRepresentation);
        } catch (NotFoundException e) {
            log.info(e.getMessage());
            return null;
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<UserRepresentation> userRepresentationList = keycloak
                .realm(realm)
                .users().list();

        return userRepresentationList.stream().map(this::fromRepresentation).collect(Collectors.toList());
    }

    private User fromRepresentation(UserRepresentation userRepresentation) {
        return new User(userRepresentation.getId(), userRepresentation.getFirstName(), userRepresentation.getLastName(), userRepresentation.getEmail());
    }
}

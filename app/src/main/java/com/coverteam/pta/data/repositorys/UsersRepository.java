package com.coverteam.pta.data.repositorys;

import com.coverteam.pta.data.models.Users;
import com.coverteam.pta.data.providers.BaseCallback;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;

/**
 * Manages data access for POJOs that are uniquely identifiable by a key, such as POJOs implementing {@link Identifiable}.
 */
public interface UsersRepository {

    CollectionReference documentCollection();

    /**
     * Checks the repository for a given id and returns a boolean representing its existence.
     * @param id the unique id of an entity.
     * @return A {@link Task} for a boolean which is 'true' if the entity for the given id exists, 'false' otherwise.
     */
    Task<Boolean> exists(String id);

    /**
     * Queries the repository for an uniquely identified entity and returns it. If the entity does
     * not exist in the repository, a new instance is returned.
     * @param id the unique id of an entity.
     * @return A {@link Task} for an entity implementing {@link Identifiable}.
     */
    Task<Users> get(String id);

    /**
     * Stores an entity in the repository so it is accessible via its unique id.
     * @param entity the entity implementing {@link Identifiable} to be stored.
     * @return An {@link Task} to be notified of failures.
     */
    Task<Void> create(Users entity, String documentName,BaseCallback callback);

    /**
     * Updates an entity in the repository
     * @param entity the new entity to be stored.
     * @return A {@link Task} to be notified of failures.
     */
    Task<Void> update(Users entity);

    /**
     * Deletes an entity from the repository.
     * @param id uniquely identifying the entity.
     * @return A {@link Task} to be notified of failures.
     */
    Task<Void> delete(String id,BaseCallback callback);

}
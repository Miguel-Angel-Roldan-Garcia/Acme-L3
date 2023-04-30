package acme.features.authenticated.note;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.group.Note;
import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedNoteRepository extends AbstractRepository {

    @Query("select note from Note note where TIME_TO_SEC(TIMEDIFF( :actualTime,note.instantiationMoment))/ 86400. < 30")
    Collection<Note> findAllNotOlderThanOneMonthNotes(Date actualTime);

    @Query("select note from Note note where note.id = :masterId")
    Note findOneNoteById(int masterId);

    @Query("select ua from UserAccount ua where ua.id = :id")
    UserAccount findOneUserAccountById(int id);

}

package ro.ubb.mp.dao.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.ubb.mp.dao.model.mysql.Message;
import ro.ubb.mp.dao.model.postgres.User;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
//    List<Message> findBySenderAndReceiver(User sender, User receiver);
//    List<Message> findBySender(User user);
//    List<Message> findByReceiver(User user);
}

package edu.poly.shop.repository;

import edu.poly.shop.entities._Paradigm;
import edu.poly.shop.entities._User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<_User,Integer> {

    @Query("select u from _User u where u.status=1 and u.email=?1")
    public Optional<_User> findByEmail(String email);
}

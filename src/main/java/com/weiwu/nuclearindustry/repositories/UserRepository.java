package com.weiwu.nuclearindustry.repositories;

import com.weiwu.nuclearindustry.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface UserRepository extends PagingAndSortingRepository<User, Long>,
        QueryByExampleExecutor<User> {
}

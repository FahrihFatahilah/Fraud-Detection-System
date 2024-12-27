package com.rest.fds.seeder;

import com.rest.fds.entity.EntityTransactionType;
import com.rest.fds.repository.TransactionTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
public class TransactionSeeder implements CommandLineRunner {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());


    private final TransactionTypeRepository transactionTypeRepository;

    public TransactionSeeder(TransactionTypeRepository transactionTypeRepository) {
        this.transactionTypeRepository = transactionTypeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (transactionTypeRepository.count() == 0) {
            List<String> transactionNames = Arrays.asList(
                    "login",
                    "transfer",
                    "activation",
                    "payment",
                    "purchase",
                    "change pin",
                    "reset pin"
            );

            LocalDateTime now = LocalDateTime.now();

            for (String name : transactionNames) {
                EntityTransactionType transaction = new EntityTransactionType(name, now, now);
                transactionTypeRepository.save(transaction);
            }

            logger.info("Transactions seeded successfully!");
        } else {
            logger.info("Transactions already exist. No seeding required.");
        }
    }
}

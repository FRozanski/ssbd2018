/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.tools;

import java.util.concurrent.atomic.AtomicLong;
import javax.ejb.AfterBegin;
import javax.ejb.AfterCompletion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author piotrek
 */
public abstract class TransactionLogger {
    private static final AtomicLong txCounter = new AtomicLong();

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private long txId;

    @AfterBegin
    private void logBeginOfTransaction() {
        txId = txCounter.getAndIncrement();

        logger.info("Transakcja (id={}, login=[TODO]) rozpoczęta.", txId);
    }

    @AfterCompletion
    private void logEndOfTransaction(boolean transactionStatus) {
        String result = transactionStatus ? "committed" : "rolled back";
        logger.info("Koniec transakcji");
        logger.info("Transakcja (id={}, login=[TODO]) zakończona ze statusem {}.", txId, result);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.ejb.sb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.foi.nwtis.dkopic2.ejb.eb.Dnevnik;
import org.foi.nwtis.dkopic2.ejb.eb.Dnevnik_;

/**
 *
 * @author domagoj
 */
@Stateless
public class DnevnikFacade extends AbstractFacade<Dnevnik> {

    @PersistenceContext(unitName = "zadaca_4_1PU")
    private EntityManager entityManager;
    private CriteriaQuery criteriaQuery;
    private CriteriaBuilder criteriaBuilder;
    private Root<Dnevnik> dnevnik;
    private List<Predicate> predicates;
    
    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    public DnevnikFacade() {
        super(Dnevnik.class);
    }
    
    /**
     * Vraća podatke nakon filtriranja
     * @return 
     */
    public List<Dnevnik> getResult() {
        criteriaQuery.orderBy(criteriaBuilder.asc(dnevnik.get(Dnevnik_.korisnik)));
        criteriaQuery.select(dnevnik).where(predicates.toArray(new Predicate[]{}));
        
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
    
    /**
     * Inicijalizira potrebne objekte
     */
    public void initialize() {
        criteriaBuilder = entityManager.getCriteriaBuilder();
        criteriaQuery = criteriaBuilder.createQuery();
        dnevnik = criteriaQuery.from(Dnevnik.class);
        predicates = new ArrayList<>();
    }
    
    /**
     * Postavlja upite za raspon datuma
     * @param startTime pocetni datum
     * @param endTime zavrsni datum
     * @throws ParseException 
     */
    public void filterByTime(String startTime, String endTime) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

        Date start = format.parse(startTime);
        Date end = format.parse(endTime);
        
        predicates.add(criteriaBuilder.greaterThanOrEqualTo(dnevnik.get(Dnevnik_.vrijeme), start));
        predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(dnevnik.get(Dnevnik_.vrijeme), end)));
    }
    
    /**
     * Postavlja upit za pocetni datum
     * @param startTime pocetni datum
     * @throws ParseException 
     */
    public void filterByStart(String startTime) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date start = format.parse(startTime);
        
        predicates.add(criteriaBuilder.greaterThanOrEqualTo(dnevnik.get(Dnevnik_.vrijeme), start));
    }
    
    /**
     * Postavlja upit za krajnji datum
     * @param endTime krajnji datum
     * @throws ParseException 
     */
    public void filterByEnd(String endTime) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date end = format.parse(endTime);
        
        predicates.add(criteriaBuilder.lessThanOrEqualTo(dnevnik.get(Dnevnik_.vrijeme), end));
    }
    
    /**
     * Postavlja upit za IP adresu
     * @param ipAddress IP adresa
     */
    public void filterByIp(String ipAddress) {
        predicates.add(criteriaBuilder.equal(dnevnik.get(Dnevnik_.ipadresa), ipAddress));
    }
    
    /**
     * Postavlja upit za trajanje akcije
     * @param ms milisekunde
     */
    public void filterBySeconds(String ms) {
        predicates.add(criteriaBuilder.equal(dnevnik.get(Dnevnik_.trajanje), Integer.parseInt(ms)));
    }
    
    /**
     * Postavlja upit za status akcije
     * @param status status akcije
     */
    public void filterByStatus(String status) {
        predicates.add(criteriaBuilder.equal(dnevnik.get(Dnevnik_.status), Integer.parseInt(status)));
    }
}

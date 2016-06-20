/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.ejb.sb;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.foi.nwtis.dkopic2.ejb.eb.Adrese;
import org.foi.nwtis.dkopic2.ejb.eb.Adrese_;

/**
 *
 * @author domagoj
 */
@Stateless
public class AdreseFacade extends AbstractFacade<Adrese> {

    @PersistenceContext(unitName = "zadaca_4_1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdreseFacade() {
        super(Adrese.class);
    }
    
    /**
     * Dohvaća sve adrese prema abecednom redoslijedu.
     * @return 
     */
    public List<Adrese> getAllDesc() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Adrese> cq = cb.createQuery(Adrese.class);

        Root<Adrese> adrese = cq.from(Adrese.class);
        cq.orderBy(cb.asc(adrese.get(Adrese_.adresa)));

        return em.createQuery(cq).getResultList();
    }
    
    /**
     * Dohvaća objekt Adrese ukoliko adresa sa određenim nazivom postoji u bazi.
     * @param name adresa
     * @return ukoliko postoji Adrese, inače null
     */
    public Adrese getByName(String name) {
        try
        {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Adrese> cq = cb.createQuery(Adrese.class);

            Root<Adrese> adrese = cq.from(Adrese.class);
            cq.where(cb.equal(adrese.get(Adrese_.adresa), name));

            return em.createQuery(cq).getSingleResult();
        }
        catch(NoResultException ex)
        {
            return null;
        }
    }
}

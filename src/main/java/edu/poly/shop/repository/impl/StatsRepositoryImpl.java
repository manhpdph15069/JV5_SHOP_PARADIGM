package edu.poly.shop.repository.impl;

import edu.poly.shop.entities._Orderdetail;
import edu.poly.shop.entities._Paradigm;
import edu.poly.shop.repository.IStatsRepository;
import edu.poly.shop.utils.HibernateUtils;
import org.hibernate.Session;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class StatsRepositoryImpl implements IStatsRepository {
    @Override
    public List<Object[]> paradigmStats() {
        Session session = HibernateUtils.getFactory().openSession();

        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);

        Root rootP = q.from(_Paradigm.class);
        Root rootO = q.from(_Orderdetail.class);

        q.where(b.equal(rootO.get("paradigm"),rootP.get("id")));

        q.multiselect(rootP.get("id"),rootP.get("paradigmName"),
                b.sum(b.prod(rootO.get("purchasedMoney"),rootO.get("purchasedQuantity")))
                );

        q.groupBy(rootP.get("id"));

        Query query = session.createQuery(q);
        return query.getResultList();
    }
}

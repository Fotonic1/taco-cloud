package com.taco_cloud.repository;

import com.taco_cloud.data.IngredientRef;
import com.taco_cloud.data.Taco;
import com.taco_cloud.data.TacoOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.Date;
import java.util.List;

@Repository
public class JdbcOrderRepository implements OrderRepository {

    @Autowired
    private JdbcOperations jdbcOperations;

    @Override
    @Transactional
    public TacoOrder save(TacoOrder order) {
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
                """
                        insert into taco_order
                        (delivery_name, delivery_street, delivery_city,
                        delivery_state, delivery_zip, cc_number,
                        cc_expiration, cc_cvv, placed_at)
                        values (?,?,?,?,?,?,?,?,?)""",
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP
        );
        pscf.setReturnGeneratedKeys(true);

        order.setPlacedAt(new Date());

        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(
                List.of(order.getDeliveryName(), order.getDeliveryStreet(), order.getDeliveryCity(),
                        order.getDeliveryState(), order.getDeliveryZip(), order.getCcNumber(),
                        order.getCcExpiration(), order.getCcCVV(), order.getPlacedAt())
        );

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(psc, keyHolder);
        long orderId = keyHolder.getKey().longValue();
        order.setId(orderId);

        List<Taco> tacos = order.getTacos();
        int i = 0;
        for (Taco taco : tacos) {
            saveTaco(orderId, i++, taco);
        }

        return order;
    }

    private long saveTaco(Long orderId, int orderKey, Taco taco) {
        taco.setCreatedAt(new Date());
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory("""
                insert into taco
                (name, created_at, taco_order, taco_order_key)
                values (?,?,?,?)""",
                Types.VARCHAR, Types.TIMESTAMP, Types.BIGINT, Types.BIGINT
        );
        pscf.setReturnGeneratedKeys(true);

        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(
                List.of(
                        taco.getName(),
                        taco.getCreatedAt(),
                        orderId, orderKey)
        );

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(psc, keyHolder);
        long tacoId = keyHolder.getKey().longValue();
        taco.setId(tacoId);

        saveIngredientsRef(tacoId, taco.getIngredients());

        return tacoId;
    }

    private void saveIngredientsRef(long tacoId, List<IngredientRef> ingredientRefs) {
        for (int i = 0; i < ingredientRefs.size(); i++) {
            jdbcOperations.update(
                    "insert into ingredient_ref (ingredient, taco, taco_key) values (?,?,?)",
                    ingredientRefs.get(i).getIngredient(), tacoId, i
            );
        }
    }
}

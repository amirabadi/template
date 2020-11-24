package com.company.common.utils.search;

import com.company.common.utils.enums.OperatorEnum;
import com.company.dto.FilterMeta;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import static com.company.common.utils.enums.OperatorEnum.EQUAL;

@Data
@AllArgsConstructor
public class SearchSpecification<T> implements Specification<T> {

    private FilterMeta filterMeta;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        switch (OperatorEnum.getOperator(filterMeta.getOperator())) {
            case EQUAL: {
                if(filterMeta.getFiledValue()!=null)
                    return criteriaBuilder.equal(root.get(filterMeta.getFiledName()), filterMeta.getFiledValue());
                else
                    return criteriaBuilder.isNull(root.get(filterMeta.getFiledName()));
            }
            case NOTEQUAL: {
                if(filterMeta.getFiledValue()!=null)
                    return criteriaBuilder.notEqual(root.get(filterMeta.getFiledName()), filterMeta.getFiledValue());
                else
                    return criteriaBuilder.isNull(root.get(filterMeta.getFiledName()));
            }
           /* case NEGATION:
                return criteriaBuilder.notEqual(root.get(criteria.getKey()), criteria.getValue());
            case LESS_THAN:
                return criteriaBuilder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString());
            case GREATER_THAN:
                return criteriaBuilder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString());*/
            default:
                return null;
        }
    }
}
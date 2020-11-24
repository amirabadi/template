package com.company.service;

import com.qorb.common.utils.enums.OperatorEnum;
import com.qorb.common.utils.search.SearchSpecification;
import com.qorb.dto.FilterMeta;
import com.qorb.dto.SortMeta;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class PageEntity<T> {
    protected Pageable createPageRequest(Integer page, Integer pagesize) {
        return PageRequest.of(page, pagesize);
    }
    protected Pageable createPageRequest(Integer page, Integer pagesize,Sort sort) {
        return PageRequest.of(page, pagesize,sort);
    }

    protected Specification<T> generateSearch(List<FilterMeta> filterMetaList)  {
        List<SearchSpecification> specList = filterMetaList.stream().map(criterion -> new SearchSpecification(criterion)).collect(Collectors.toList());
        Specification<T> specs =  andSpecification(specList).orElseThrow(() -> new IllegalArgumentException("No criteria provided"));
        return specs;
    }

    private Optional<Specification<T>> andSpecification(List<SearchSpecification> searchSpecifications){
        Iterator<SearchSpecification> itr = searchSpecifications.iterator();
        if (itr.hasNext()) {
            Specification<T> spec = Specification.where(itr.next());
            while (itr.hasNext()) {
                spec = spec.and(itr.next());
            }
            return Optional.of(spec);
        }
        return Optional.empty();
    }

    public Sort generateSort(List<SortMeta> sortMetas){
        List<Sort> sortList=generateSortList(sortMetas);
        Sort sort = andSort(sortList).orElse(Sort.unsorted());
        return sort;
    }
    private Optional<Sort> andSort(List<Sort> sorts){
        Iterator<Sort> itr = sorts.iterator();
        if (itr.hasNext()) {
            Sort sort = (itr.next());
            while (itr.hasNext()) {
                sort = sort.and(itr.next());
            }
            return Optional.of(sort);
        }
        return Optional.empty();
    }
    private List<Sort> generateSortList(List<SortMeta> sortMetas) {
        return sortMetas.stream().map((sortMeta) -> {
            switch (OperatorEnum.getOperator(sortMeta.getDirection())) {
                case ASCENDING:
                    return Sort.by(Order.asc(sortMeta.getFiledName()));
                case DESCENDING:
                    return Sort.by(Order.desc(sortMeta.getFiledName()));
                default:
                    return null;
            }
        }).filter((sort) -> sort != null).collect(Collectors.toList());
    }
    /*private <T, V extends Specification<T>> Optional<Specification<T>> andSpecification(List<V> criteria) {
        Iterator<V> itr = criteria.iterator();
        if (itr.hasNext()) {
            Specification<T> spec = Specification.where(itr.next());
            while (itr.hasNext()) {
                spec = spec.and(itr.next());
            }
            return Optional.of(spec);
        }
        return Optional.empty();
    }*/
    public FilterMeta deleteEvent(){
        FilterMeta filterMeta = new FilterMeta();
        filterMeta.setFiledName("event");
        filterMeta.setFiledValue("DELETE");
        filterMeta.setOperator("neq");
        return filterMeta;
    }
}

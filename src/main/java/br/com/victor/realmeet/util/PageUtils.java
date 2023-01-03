package br.com.victor.realmeet.util;

import br.com.victor.realmeet.exception.InvalidOrderByFielException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

public final class PageUtils {

    private PageUtils() {
    }

    public static Pageable newPageable(Integer page, Integer limit, int maxLimit, String orderBy, List<String> validSortableFields) {
        int definedPage = isNull(page) ? 0 : page;
        int definedLimit = isNull(limit) ? maxLimit : Math.min(limit, maxLimit);
        Sort definedSort = parseOrderByFields(orderBy, validSortableFields);
        return PageRequest.of(definedPage, definedLimit, definedSort);
    }

    private static Sort parseOrderByFields(String orderBy, List<String> validSortableFields) {

        if (isNull(validSortableFields) || validSortableFields.isEmpty()) {
            throw new IllegalArgumentException("No valid sortable fields were defined");
        }

        if (StringUtils.isBlank(orderBy)) {
            return Sort.unsorted();
        }

        List<Sort.Order> orderList = Stream
                .of(orderBy.split(","))
                .map(
                        field -> {
                            String fieldName;
                            Sort.Order order;
                            if (field.startsWith("-")) {
                                fieldName = field.substring(1);
                                order = Sort.Order.desc(fieldName);
                            } else {
                                fieldName = field;
                                order = Sort.Order.asc(fieldName);
                            }
                            if (!validSortableFields.contains(fieldName)) {
                                throw new InvalidOrderByFielException();
                            }

                            return order;
                        }
                ).collect(Collectors.toList());

        return Sort.by(orderList);
    }


}

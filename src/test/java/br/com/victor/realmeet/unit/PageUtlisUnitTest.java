package br.com.victor.realmeet.unit;

import br.com.victor.realmeet.core.BaseUnitTest;
import br.com.victor.realmeet.exception.InvalidOrderByFielException;
import br.com.victor.realmeet.util.PageUtils;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collections;

import static br.com.victor.realmeet.domain.entity.Allocation.SORTABLE_FIELDS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PageUtlisUnitTest extends BaseUnitTest {

    @Test
    void testNewPageableWhenPageIsNullAndLimitIsNulAndOrderByIsNull(){
        Pageable pageable = PageUtils.newPageable(null, null, 10, null, SORTABLE_FIELDS);
        assertEquals(0,pageable.getPageNumber());
        assertEquals(10,pageable.getPageSize());
        assertEquals(Sort.unsorted(),pageable.getSort());
    }

    @Test
    void testNewPageableWhenPageIsNegative(){
        assertThrows(IllegalArgumentException.class,()->PageUtils.newPageable(-1, null, 10, null, SORTABLE_FIELDS));
    }

    @Test
    void testNewPageableWheLimitIsInvalid(){
        assertThrows(IllegalArgumentException.class,()->PageUtils.newPageable(null, 0, 10, null, SORTABLE_FIELDS));
    }


    @Test
    void testNewPageableWhenLimitExceedsMaximum(){
        Pageable pageable = PageUtils.newPageable(null, 20, 10, null, SORTABLE_FIELDS);
        assertEquals(10,pageable.getPageSize());

    }


    @Test
    void testNewPageableWhenOrdeByIsNull(){
        assertThrows(IllegalArgumentException.class,()->PageUtils.newPageable(null, 0, 10, null, null));

    }

    @Test
    void testNewPageableWhenOrdeByIsEmpty(){
        assertThrows(IllegalArgumentException.class,()->PageUtils.newPageable(null, 0, 10, null, Collections.emptyList()));

    }

    @Test
    void testNewPageableWhenOrderByAscIsvalid(){
        Pageable pageable = PageUtils.newPageable(null, null, 10, SORTABLE_FIELDS.get(0), SORTABLE_FIELDS);
        assertEquals(Sort.by(Sort.Order.asc(SORTABLE_FIELDS.get(0))),pageable.getSort());
    }

    @Test
    void testNewPageableWhenOrderByDescIsvalid(){
        Pageable pageable = PageUtils.newPageable(null, null, 10, "-"+SORTABLE_FIELDS.get(0), SORTABLE_FIELDS);
        assertEquals(Sort.by(Sort.Order.desc(SORTABLE_FIELDS.get(0))),pageable.getSort());
    }

    @Test
    void testNewPageableWhenOrdeByFieldIsInvalid() {
        assertThrows(InvalidOrderByFielException.class,()->PageUtils.newPageable(null, 0, 10, "invalid", SORTABLE_FIELDS));

    }


}

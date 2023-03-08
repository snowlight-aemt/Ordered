package com.practice.order.infrastructure.item.itemOption;

import com.practice.order.domain.item.ItemOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemOptionRepository extends JpaRepository<ItemOption, Long> {
}

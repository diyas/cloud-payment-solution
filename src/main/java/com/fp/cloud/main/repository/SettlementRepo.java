package com.fp.cloud.main.repository;

import com.fp.cloud.main.domain.Settlement;
import com.fp.cloud.main.global.TrStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettlementRepo extends JpaRepository<Settlement, Long> {
    Settlement findById(String id);
    Settlement findByIdAndStatus(String id, TrStatusEnum status);

}

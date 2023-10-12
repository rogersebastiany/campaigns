package br.com.uol.campaigns.campaign;

/*
 * ID único
 * Nome da campanha
 * Orçamento da campanha
 * Publico da campanha
 * Data de início
 * Data de término
 * Status da campanha (ativa, pausada, concluída, etc.)
 */

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "tbl_campaigns")
public class CampaignModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(length = 50)
    private String name;
    private long budget;
    private String audience;
    private LocalDateTime startsAt;
    private LocalDateTime endsAt;

    private UUID idUser;

    @CreationTimestamp
    private LocalDateTime createdAt;
}

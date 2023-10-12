package br.com.uol.campaigns.campaign;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ICampaignRepository extends JpaRepository<CampaignModel, UUID> {

}

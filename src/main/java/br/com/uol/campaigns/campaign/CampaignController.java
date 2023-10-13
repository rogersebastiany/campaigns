package br.com.uol.campaigns.campaign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/campanhas")
public class CampaignController {

    @Autowired
    private ICampaignRepository campaignRepository;

    @PostMapping("")
    public CampaignModel create(@RequestBody CampaignModel campaignModel) {
        return this.campaignRepository.save(campaignModel);
    }

}

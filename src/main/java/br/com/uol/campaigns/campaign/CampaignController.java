package br.com.uol.campaigns.campaign;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.uol.campaigns.utils.Utils;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/campanhas")
public class CampaignController {

    @Autowired
    private ICampaignRepository campaignRepository;

    @PostMapping("")
    public ResponseEntity create(@RequestBody CampaignModel campaignModel, HttpServletRequest request) {
        // se o usuario chegou aqui, eh obrigado a ter esse atributo no request
        var idUser = request.getAttribute("idUser");
        if (campaignModel.getIdUser().equals(idUser))
            return ResponseEntity.ok().body(this.campaignRepository.save(campaignModel));
        // se cair aqui, o usuario logado tentou criar campanhas para outro usuario
        return ResponseEntity.status(401).body("Usuario não tem permissão para criar essa campanha");
    }

    @GetMapping("")
    public List<CampaignModel> list(HttpServletRequest request) {
        var idUser = request.getAttribute("idUser");
        return this.campaignRepository.findByIdUser((UUID) idUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody CampaignModel campaignModel, HttpServletRequest request, @PathVariable UUID id) {
        var idUser = request.getAttribute("idUser");
        if (! campaignModel.getIdUser().equals(idUser))
            return ResponseEntity.status(401).body("Usuario não tem permissão para alterar essa campanha");

        var campaign = this.campaignRepository.findById(id).orElse(null);
        if (campaign == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Campanha não encontrada");

        Utils.copyNonNullProperties(campaignModel, campaign);
        return ResponseEntity.ok().body(this.campaignRepository.save(campaign));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(HttpServletRequest request, @PathVariable UUID id) {
        var idUser = request.getAttribute("idUser");
        var campaign = this.campaignRepository.findById(id).orElse(null);

        if (campaign == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Campanha não encontrada");
        if (! campaign.getIdUser().equals(idUser))
            return ResponseEntity.status(401).body("Usuario não tem permissão para alterar essa campanha");

        this.campaignRepository.delete(campaign);
        return ResponseEntity.ok().build();
    }

}

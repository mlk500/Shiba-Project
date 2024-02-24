package shiba.backend.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shiba.backend.app.BL.GameBL;
import shiba.backend.app.entities.Game;
import shiba.backend.app.util.Constants;

@RestController
@RequestMapping(Constants.GAME_ENDPOINT)
public class GameController {

}

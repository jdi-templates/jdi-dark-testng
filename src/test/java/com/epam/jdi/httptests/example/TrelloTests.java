package com.epam.jdi.httptests.example;

import com.epam.jdi.httptests.example.dto.*;
import com.epam.jdi.httptests.utils.TrelloDataGenerator;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

import static com.epam.http.requests.ServiceInit.init;
import static com.epam.jdi.tools.LinqUtils.map;
import static org.testng.Assert.assertTrue;

/**
 * Example of creating business flow tests
 */
public class TrelloTests {

    @BeforeClass
    public void initService() {
        init(TrelloService.class);
    }

    @Test
    public void createCardInBoard() {

        //Crate board
        Board board = TrelloDataGenerator.generateBoard();
        Board createdBoard = TrelloService.createBoard(board);
        Board gotBoard = TrelloService.getBoard(createdBoard.id);
        Assert.assertEquals(gotBoard.name, createdBoard.name, "Name of created board is incorrect");

        //Create list
        TrelloList tList = TrelloDataGenerator.generateList(createdBoard);
        TrelloList createdList = TrelloService.createList(tList);

        //Create Card
        Card card = TrelloDataGenerator.generateCard(createdBoard, createdList);
        Card createdCard = TrelloService.addNewCardToBoard(card);

        //Check that card was added
        Board cardBoard = TrelloService.getCardBoard(createdCard.id);
        Assert.assertEquals(cardBoard.name, board.name, "Card wasn't added to board");
    }

    @Test
    public void assignBoardToOrganization() {

        //Create organization
        Organization organization = TrelloDataGenerator.generateOrganization();
        Organization createOrg = TrelloService.createOrganization(organization);

        //Crate board
        Board board = TrelloDataGenerator.generateBoard();
        board.idOrganization = createOrg.id;
        TrelloService.createBoard(board);

        //Check that organization contains created board
        List<Board> boards = TrelloService.getOrganizationBoards(createOrg);
        assertTrue(map(boards, b -> b.name).contains(board.name), "Board wasn't added to organization");

    }
}

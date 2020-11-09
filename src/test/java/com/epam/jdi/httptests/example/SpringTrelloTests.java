package com.epam.jdi.httptests.example;

import com.epam.jdi.httptests.example.dto.*;
import com.epam.jdi.httptests.utils.TrelloDataGenerator;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.epam.http.requests.ServiceInit.init;

/**
 * Example of tests with Spring using
 */
@SpringBootTest
@ContextConfiguration(classes = {JDIDarkSpringAutomatedTest.class})
public class SpringTrelloTests extends AbstractTestNGSpringContextTests {

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
}

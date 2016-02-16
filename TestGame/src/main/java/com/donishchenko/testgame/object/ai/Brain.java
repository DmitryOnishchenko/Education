package com.donishchenko.testgame.object.ai;

import com.donishchenko.testgame.object.GameObject;

import java.util.HashMap;
import java.util.Map;

public class Brain {

    private GameObject gameObject;
    private Idea[] ideas;
    private Map<String, Idea> allIdeas;
    private Map<String, Idea> availableIdeas;
    private Idea mainIdea;
    private boolean locked;

    public Brain(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public void init() {
        ideas = new Idea[3];

        /* Move */
        Idea simpleMoveIdea = new SimpleMoveIdea(gameObject, 1);
        simpleMoveIdea.init();
        ideas[0] = simpleMoveIdea;

        /* Search enemy */
        Idea searchEnemyIdea = new SearchEnemyIdea(gameObject, 10);
        ideas[1] = searchEnemyIdea;

        /* Attack */
        Idea simpleAttackIdea = new SimpleAttackIdea(gameObject, 5);
        simpleAttackIdea.init();
        ideas[2] = simpleAttackIdea;

        /* Set default main idea */
        mainIdea = simpleMoveIdea;
        gameObject.action = mainIdea.action;

        // TODO new ideas system
        allIdeas = new HashMap<>();
        allIdeas.put(simpleMoveIdea.name, simpleMoveIdea);
        allIdeas.put(searchEnemyIdea.name, searchEnemyIdea);
        allIdeas.put(simpleAttackIdea.name, simpleAttackIdea);

//        availableIdeas.put(searchEnemyIdea.name, searchEnemyIdea);
//        availableIdeas.put(simpleAttackIdea.name, simpleAttackIdea);
    }

    public void update() {
//        if (!locked) {
//            for (Idea idea : ideas) {
//                if (idea.priority >= mainIdea.priority && idea.think()) {
//                    mainIdea = idea;
//                }
//            }
//            gameObject.action = mainIdea.action;
//        }

//        for ()
    }

}

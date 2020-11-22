# Chess-AI-
Chess Game in AI with Minimax Algorithm

  How to Play Chess Game?
  
    Basic rules 
    Chess is a two-player game, where one player is assigned white pieces and the other black. Each player has 16 pieces to start the game: one king, one queen,
    two rooks, two bishops, two knights and eight pawns. 
    
    Aim of the game 
    The object of the game is to capture the other player's king. It is said to be checkmated and the game is over. 
    
    Start of the game
    The game is started in the position shown below on a chess board consisting of 64 squares in an 8x8 grid. White starts to the game first.
    
    End of the Game Checkmate 
    The primary objective in chess is to checkmate your opponent's King. When a King cannot avoid capture then it is checkmated and the game is immediately over.

  Minimax Algorithm
  
    Minimax is an algorithm designed to maximize gain and minimize loss in the worst-case scenario of a game play. It's perfect play for deterministic fully
    observables games. The idea is to choose the next move with the highest minimax value (the best achievable playoff against the opponent playing their best
    possible move).
    
    The Minimax is a recursive algorithm which can be used for solving two-player zero-sum games. In each state of the game we associate a 
    value. The Minimax algorithm searches through the space of possible game states creating a tree which is expanded until it reaches a
    predefined depth. Once those leaf states are reached, their values are used to estimate the ones of the intermediate nodes.
    
  How does the algorithm work?
  
    There are two players involved in a game, called MIN and MAX. The player MAX tries to get the highest possible score and MIN tries to
    get the lowest possible score, i.e., MIN and MAX try to act opposite of each other.
    
    The initial state is the first layer that defines that the board is blank it’s MAX’s turn to play.
    Successor function lists all the possible successor moves. It is defined for all the layers in the tree.
    Terminal State is the last layer of the tree that shows the final state, i.e whether the player MAX wins, loses, or ties with the opponent.
    
  Conclusion
  
    Minimax algorithm is one of the most popular algorithms for computer board games. It is widely applied in turn based games. It can be a good choice when players
    have complete information about the game.
    
    It may not be the best choice for the games with exceptionally high branching factor (e.g. game of GO). Nonetheless, given a proper implementation, it can be a
    pretty smart AI.



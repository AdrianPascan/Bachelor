class GameOver (Exception):
    
    def __init__(self, message):
        
        self._message = message
        
    
    def __str__(self):
        
        return "Game over! - " + self._message
    

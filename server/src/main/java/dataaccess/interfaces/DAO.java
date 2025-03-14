package dataaccess.interfaces;

public interface DAO {
    UserDAO makeUserDAO();
    GameDAO makeGameDAO();
    AuthDAO makeAuthDAO();
    ResetDAO makeResetDAO();
}

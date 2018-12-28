package NetWork.TO;

public enum ActionEnum {
    LOGIN,//data = Vector<String>[id,password]
    REGISTER,//data = Vector<String>[String password, String name, String personId, String email, ACCOUNT_TYPE type]
    WITHDRAW,//data = Vector<String>[id,money]
    DEPOSIT,//data = Vector<String>[id,money]
    TRANSFER,//data = Vector<String>[fromId,toId,money]
    REQUEST_LOAN,
    PAY_LOAN,
    SET_CEILING,
    RESPONSE,//data = Account
    DISCONNECT,
    ERROR,
    DEBUG,
}

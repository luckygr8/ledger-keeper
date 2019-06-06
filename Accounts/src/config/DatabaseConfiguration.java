package config;

public interface DatabaseConfiguration {

    String CONNECTION_URL = "jdbc:sqlite:accounts.db";
    
    String ACCOUNT_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS accountinfo (\n"
            + "	accountid integer PRIMARY KEY AUTOINCREMENT,\n"
            + "	accountname varchar(500) NOT NULL UNIQUE,\n"
            + "	openingbalance float,\n"
            + "	currentbalance float,\n"
            + " creationdate datetime\n"            
            + ");";

    String ENTRYINFO_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS entryinfo (\n"
            + "	entryid integer PRIMARY KEY AUTOINCREMENT,\n"
            + "	particulars text,\n"
            + "	entrytype varchar(10),\n"
            + " amount float,\n"
            + " accountid integer,\n"
            + " entrydate datetime,\n"
            + " foreign key(accountid) references accountinfo(accountid)"            
            + ");";

    String CLIENTINFO_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS clientinfo (\n"
            + "	clientid integer PRIMARY KEY AUTOINCREMENT,\n"
            + "	name varchar(100),\n"
            + "	address varchar(500),\n"
            + " totalincome float,\n"
            + " tax float,\n"
            + " tds float,\n"
            + " refund float,\n"
            + " pan varchar(20) UNIQUE,\n"
            + " dob date,\n"
            + " acknowledgement text,\n"
            + " remarks text,\n"
            + " entrydate date"
            + ");";
}

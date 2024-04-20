package networking.networking.enums;

import lombok.Getter;

@Getter
public enum CountryEnum {
    ALBANIA(1, "Албания"),
    ANDORRA(2, "Андора"),
    AUSTRIA(3, "Австрия"),
    BELARUS(4, "Беларус"),
    BELGIUM(5, "Белгия"),
    BOSNIA_AND_HERZEGOVINA(6, "Босна и Херцеговина"),
    BULGARIA(7, "България"),
    CROATIA(8, "Хърватия"),
    CYPRUS(9, "Кипър"),
    CZECH_REPUBLIC(10, "Чехия"),
    DENMARK(11, "Дания"),
    ESTONIA(12, "Естония"),
    FINLAND(13, "Финландия"),
    FRANCE(14, "Франция"),
    GERMANY(15, "Германия"),
    GREECE(16, "Гърция"),
    HUNGARY(17, "Унгария"),
    ICELAND(18, "Исландия"),
    IRELAND(19, "Ирландия"),
    ITALY(20, "Италия"),
    KAZAKHSTAN(21, "Казахстан"),
    KOSOVO(22, "Косово"),
    LATVIA(23, "Латвия"),
    LIECHTENSTEIN(24, "Лихтенщайн"),
    LITHUANIA(25, "Литва"),
    LUXEMBOURG(26, "Люксембург"),
    MACEDONIA(27, "Македония"),
    MALTA(28, "Малта"),
    MOLDOVA(29, "Молдова"),
    MONACO(30, "Монако"),
    MONTENEGRO(31, "Черна гора"),
    NETHERLANDS(32, "Холандия"),
    NORWAY(33, "Норвегия"),
    POLAND(34, "Полша"),
    PORTUGAL(35, "Португалия"),
    ROMANIA(36, "Румъния"),
    RUSSIA(37, "Русия"),
    SAN_MARINO(38, "Сан Марино"),
    SERBIA(39, "Сърбия"),
    SLOVAKIA(40, "Словакия"),
    SLOVENIA(41, "Словения"),
    SPAIN(42, "Испания"),
    SWEDEN(43, "Швеция"),
    SWITZERLAND(44, "Швейцария"),
    TURKEY(45, "Турция"),
    UKRAINE(46, "Украйна"),
    UNITED_KINGDOM(47, "Обединено кралство");

    private final long id;
    private final String label;

    CountryEnum(long id, String label) {
        this.id = id;
        this.label = label;
    }

}

package networking.networking.country;

public enum Country {
    ALBANIA("Албания"),
    ANDORRA("Андора"),
    AUSTRIA("Австрия"),
    BELARUS("Беларус"),
    BELGIUM("Белгия"),
    BOSNIA_AND_HERZEGOVINA("Босна и Херцеговина"),
    BULGARIA("България"),
    CROATIA("Хърватия"),
    CYPRUS("Кипър"),
    CZECH_REPUBLIC("Чехия"),
    DENMARK("Дания"),
    ESTONIA("Естония"),
    FINLAND("Финландия"),
    FRANCE("Франция"),
    GERMANY("Германия"),
    GREECE("Гърция"),
    HUNGARY("Унгария"),
    ICELAND("Исландия"),
    IRELAND("Ирландия"),
    ITALY("Италия"),
    KAZAKHSTAN("Казахстан"),
    KOSOVO("Косово"),
    LATVIA("Латвия"),
    LIECHTENSTEIN("Лихтенщайн"),
    LITHUANIA("Литва"),
    LUXEMBOURG("Люксембург"),
    MACEDONIA("Македония"),
    MALTA("Малта"),
    MOLDOVA("Молдова"),
    MONACO("Монако"),
    MONTENEGRO("Черна гора"),
    NETHERLANDS("Холандия"),
    NORWAY("Норвегия"),
    POLAND("Полша"),
    PORTUGAL("Португалия"),
    ROMANIA("Румъния"),
    RUSSIA("Русия"),
    SAN_MARINO("Сан Марино"),
    SERBIA("Сърбия"),
    SLOVAKIA("Словакия"),
    SLOVENIA("Словения"),
    SPAIN("Испания"),
    SWEDEN("Швеция"),
    SWITZERLAND("Швейцария"),
    TURKEY("Турция"),
    UKRAINE("Украйна"),
    UNITED_KINGDOM("Обединено кралство");

    private final String label;

    Country(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

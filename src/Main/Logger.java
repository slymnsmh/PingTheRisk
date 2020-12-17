package Main;

class Logger
{
    private String logString = "";
    StyleStrategy strategy;

    public Logger(StyleStrategy strategy)
    {
        this.strategy = strategy;
    }

    public String getLog()
    {
        return strategy.createLog(logString);
    }
    public void log(String s)
    {
        logString = s;
    }
}

interface StyleStrategy
{
    public String createLog(String str);
}

class ItalicLoggerDecorator implements StyleStrategy
{
    StyleStrategy strategy;
    public ItalicLoggerDecorator(StyleStrategy strategy)
    {
        this.strategy = strategy;
    }

    public String createLog(String str)
    {
        str = "<it>"+str+"</it>";
        return strategy.createLog(str);
    }
}

class BoldLoggerDecorator implements StyleStrategy
{
    StyleStrategy strategy;
    public BoldLoggerDecorator(StyleStrategy strategy)
    {
        this.strategy = strategy;
    }

    public String createLog(String str)
    {
        str = "<b>"+str+"</b>";
        return strategy.createLog(str);
    }

}

class UnderlinedLoggerDecorator implements StyleStrategy
{
    StyleStrategy strategy;

    public UnderlinedLoggerDecorator( StyleStrategy strategy)
    {
        this.strategy = strategy;
    }

    public String createLog( String str )
    {
        str = "<unl>"+str+"</unl>";
        return strategy.createLog(str);
    }
}

class NotDecorator implements StyleStrategy
{
    @Override
    public String createLog( String str )
    {
        return str;
    }
}

class Test
{
    public static void main(String[] args)
    {
        Logger logger = new Logger(new ItalicLoggerDecorator(
                new BoldLoggerDecorator(
                        new UnderlinedLoggerDecorator(
                            new NotDecorator()
                ))
        ));
        logger.log("Deneme");
        System.err.println(logger.getLog());
    }
}
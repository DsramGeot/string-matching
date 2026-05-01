import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class HTMLGenerator {

    public static final String[] COMMON_WORDS = {
            "the", "of", "and", "to", "in", "for", "is", "on", "that", "by",
            "this", "with", "you", "it", "not", "or", "be", "are", "from", "at",
            "as", "your", "all", "have", "new", "more", "an", "was", "we", "will",
            "home", "can", "us", "about", "if", "page", "my", "has", "search", "free",
            "but", "our", "one", "other", "do", "no", "information", "time", "they", "site",
            "he", "up", "may", "what", "which", "their", "news", "out", "use", "any",
            "there", "see", "only", "so", "his", "when", "contact", "here", "business", "who",
            "web", "also", "now", "help", "get", "view", "online", "first", "am", "been",
            "would", "how", "were", "me", "services", "some", "these", "click", "its", "like",
            "service", "than", "find", "price", "date", "back", "top", "people", "had", "list",
            "name", "just", "over", "state", "year", "day", "into", "email", "two", "health",
            "world", "re", "next", "used", "go", "work", "last", "most", "products", "music",
            "buy", "data", "make", "them", "should", "product", "system", "post", "her", "city",
            "add", "policy", "number", "such", "please", "available", "copyright", "support", "message", "after",
            "best", "software", "then", "good", "video", "well", "where", "info", "rights", "public",
            "books", "high", "school", "through", "each", "links", "she", "review", "years", "order",
            "very", "privacy", "book", "items", "company", "read", "group", "need", "many",
            "user", "said", "does", "set", "under", "general", "research", "university", "mail", "full",
            "map", "reviews", "program", "life", "know", "games", "way", "days", "management", "part",
            "could", "great", "united", "hotel", "real", "item", "international", "center", "must", "store",
            "travel", "comments", "made", "development", "report", "off", "member", "details", "line", "terms",
            "before", "hotels", "did", "send", "right", "type", "because", "local", "those", "using",
            "results", "office", "education", "national", "car", "design", "take", "posted", "internet", "address",
            "community", "within", "states", "area", "want", "phone", "shipping", "reserved", "subject", "between",
            "forum", "family", "long", "based", "code", "show", "even", "black", "check", "special",
            "prices", "website", "index", "being", "women", "much", "sign", "file", "link", "open",
            "today", "technology", "south", "case", "project", "same", "pages", "version", "section", "own",
            "found", "sports", "house", "related", "security", "both", "county", "photo", "game", "members",
            "power", "while", "care", "network", "down", "computer", "systems", "three", "total", "place",
            "end", "following", "download", "him", "without", "per", "access", "think", "north", "resources",
            "current", "posts", "big", "media", "law", "control", "water", "history", "pictures", "size",
            "art", "personal", "since", "including", "guide", "shop", "directory", "board", "location", "change",
            "white", "text", "small", "rating", "rate", "government", "children", "during", "return", "students",
            "shopping", "account", "times", "sites", "level", "digital", "profile", "previous", "form", "events",
            "love", "old", "main", "call", "hours", "image", "department", "title", "description", "non",
            "insurance", "another", "why", "shall", "property", "class", "cd", "still", "money", "quality",
            "every", "listing", "content", "country", "private", "little", "visit", "save", "tools", "low",
            "reply", "customer", "december", "compare", "movies", "include", "college", "value", "article", "man",
            "card", "jobs", "provide", "food", "source", "author", "different", "press", "learn", "sale",
            "around", "print", "course", "job", "process", "teen", "room", "stock", "training", "too",
            "credit", "point", "join", "science", "men", "categories", "advanced", "west", "sales", "look",
            "english", "left", "team", "estate", "box", "conditions", "select", "windows", "photos", "gay",
            "thread", "week", "category", "note", "live", "large", "gallery", "table", "register", "however",
            "june", "october", "november", "market", "library", "really", "action", "start", "series", "model",
            "features", "air", "industry", "plan", "human", "provided", "tv", "yes", "required", "second",
            "hot", "accessories", "cost", "movie", "forums", "march", "september", "better", "say", "questions",
            "july", "going", "medical", "test", "friend", "come", "server", "pc", "study", "application",
            "cart", "staff", "articles", "feedback", "again", "play", "looking", "issues", "april", "never",
            "users", "complete", "street", "topic", "comment", "financial", "things", "working", "against", "standard",
            "tax", "person", "below", "mobile", "less", "got", "blog", "party", "payment", "equipment",
            "login", "student", "let", "programs", "offers", "legal", "above", "recent", "park", "stores",
            "side", "act", "problem", "red", "give", "memory", "performance", "social", "august", "quote",
            "language", "story", "sell", "options", "experience", "rates", "create", "key", "body", "young",
            "america", "important", "field", "few", "east", "paper", "single", "age", "activities", "club",
            "example", "girls", "additional", "password", "latest", "something", "road", "gift", "question", "changes",
            "night", "ca", "hard", "oct", "pay", "four", "poker", "status", "browse", "issue",
            "range", "building", "seller", "court", "february", "always", "result", "audio", "light", "write",
            "war", "offer", "blue", "groups", "easy", "given", "files", "event", "release", "analysis",
            "request", "fax", "making", "picture", "needs", "possible", "might", "professional", "yet", "month",
            "major", "star", "areas", "future", "space", "committee", "hand", "sun", "cards", "problems",
            "meeting", "become", "interest", "id", "child", "keep", "enter", "share", "similar", "garden",
            "schools", "million", "added", "reference", "companies", "listed", "baby", "learning", "energy", "run",
            "delivery", "net", "popular", "term", "film", "stories", "put", "computers", "journal", "reports",
            "co", "try", "welcome", "central", "images", "president", "notice", "original", "head", "radio",
            "until", "cell", "color", "self", "council", "away", "includes", "track", "discussion", "archive",
            "once", "others", "entertainment", "agreement", "format", "least", "society", "months", "log", "safety",
            "friends", "sure", "faq", "trade", "edition", "cars", "messages", "marketing", "tell", "further",
            "updated", "association", "able", "having", "provides", "fun", "already", "green", "studies", "close",
            "common", "drive", "specific", "several", "gold", "living", "sep", "collection", "called", "short",
            "arts", "lot", "ask", "display", "limited", "powered", "solutions", "means", "director", "daily",
            "beach", "past", "natural", "whether", "due", "electronics", "five", "upon", "period", "planning",
            "database", "says", "official", "weather", "land", "average", "done", "technical", "window", "pro",
            "region", "island", "record", "direct", "microsoft", "conference", "environment", "records", "st",
            "district",
            "calendar", "costs", "style", "front", "statement", "update", "parts", "ever", "downloads", "early",
            "miles", "sound", "resource", "present", "applications", "either", "ago", "document", "word", "works",
            "material", "bill", "written", "talked", "federal", "hosting", "rules", "final", "adult", "tickets",
            "thing", "centre", "requirements", "via", "cheap", "kids", "finance", "true", "minutes", "else",
            "mark", "third", "rock", "gifts", "reading", "topics", "bad", "individual", "tips", "plus",
            "auto", "cover", "usually", "edit", "together", "videos", "percent", "fast", "function", "fact",
            "unit", "getting", "global", "tech", "meet", "far", "economic", "en", "player", "projects",
            "lyrics", "often", "subscribe", "submit", "amount", "watch", "included", "feel", "though", "bank",
            "risk", "thanks", "everything", "deals", "various", "words", "linux", "production", "commercial", "weight",
            "town", "heart", "advertising", "received", "choose", "treatment", "newsletter", "archives", "points",
            "knowledge",
            "magazine", "error", "camera", "girl", "currently", "construction", "toys", "registered", "clear", "golf",
            "receive", "domain", "methods", "chapter", "makes", "protection", "policies", "loan", "wide", "beauty",
            "manager", "position", "taken", "sort", "listings", "models", "known", "half", "cases", "step",
            "engineering", "simple", "quick", "none", "wireless", "license", "friday", "lake", "whole", "annual",
            "published", "later", "basic", "shows", "corporate", "church", "method", "purchase", "customers", "active",
            "response", "practice", "hardware", "figure", "materials", "fire", "holiday", "chat", "enough", "designed",
            "along", "death", "writing", "speed", "countries", "loss", "face", "brand", "discount", "higher",
            "effects", "created", "remember", "standards", "oil", "bit", "yellow", "political", "increase", "advertise",
            "kingdom", "base", "near", "environmental", "thought", "stuff", "storage", "oh", "doing", "loans",
            "shoes", "entry", "stay", "nature", "orders", "availability", "summary", "turn", "mean", "growth",
            "notes", "agency", "king", "monday", "european", "activity", "copy", "although", "drug", "pics",
            "western", "income", "force", "cash", "employment", "overall", "bay", "river", "commission", "ad",
            "package", "contents", "seen", "players", "engine", "port", "album", "regional", "stop", "supplies",
            "started", "administration", "bar", "institute", "views", "plans", "double", "dog", "build", "screen",
            "exchange", "types", "soon", "sponsored", "lines", "electronic", "continue", "across", "benefits", "needed",
            "season", "apply", "someone", "held", "anything", "printer", "condition", "effective", "believe",
            "organization",
            "effect", "asked", "mind", "sunday", "selection", "casino", "lost", "tour", "menu", "volume"
    };

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Length and content type is not provided!");
            System.exit(1);
        }

        int length = Integer.parseInt(args[0]);
        int contentType = Integer.parseInt(args[1]); // 0 for English, 1 for Bitstring
        htmlGenerator(length, contentType);
    }

    public static void htmlGenerator(int length, int contentType) {

        File file = new File(contentType == 0 ? "English.html" : "BitString.html");
        try {
            FileWriter writer = new FileWriter(file);
            StringBuilder str = new StringBuilder();
            Random random = new Random();

            str.append("<HTML><BODY>");
            if (contentType == 0) {
                int currentLength = 0;
                int arrayLength = COMMON_WORDS.length;
                int randomIndex = 0;
                while (currentLength < length) {
                    if (currentLength % 400 == 0)
                        str.append("\n");
                    randomIndex = random.nextInt(arrayLength);
                    String word = COMMON_WORDS[randomIndex];
                    str.append(word);
                    str.append(" ");
                    currentLength += word.length() + 1;
                }
            } else if (contentType == 1) {
                int binary = 0;
                for (int i = 0; i < length; i++) {

                    if (i % 400 == 0)
                        str.append("\n");
                    binary = random.nextInt(2);
                    str.append(binary);
                }
            } else {
                System.out.println("Invalid content type input!");
                System.exit(1);
            }
            str.append("\n</BODY></HTML>");

            writer.write(str.toString());
            writer.close();
        } catch (IOException e) {
            System.out.println("File could not be created!");
            System.exit(1);
        }
    }
}
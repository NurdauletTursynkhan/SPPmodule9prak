interface IReport {
    String generate();
}

class SalesReport implements IReport {
    public String generate() {
        return "Sales Report: Total Sales = 1000";
    }
}

class UserReport implements IReport {
    public String generate() {
        return "User Report: Active Users = 150";
    }
}

abstract class ReportDecorator implements IReport {
    protected IReport report;

    public ReportDecorator(IReport report) {
        this.report = report;
    }

    public String generate() {
        return report.generate();
    }
}

class DateFilterDecorator extends ReportDecorator {
    private String startDate;
    private String endDate;

    public DateFilterDecorator(IReport report, String startDate, String endDate) {
        super(report);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String generate() {
        return super.generate() + ", Filtered from " + startDate + " to " + endDate;
    }
}

class SortingDecorator extends ReportDecorator {
    private String sortBy;

    public SortingDecorator(IReport report, String sortBy) {
        super(report);
        this.sortBy = sortBy;
    }

    public String generate() {
        return super.generate() + ", Sorted by " + sortBy;
    }
}

class CsvExportDecorator extends ReportDecorator {
    public CsvExportDecorator(IReport report) {
        super(report);
    }

    public String generate() {
        return super.generate() + ", Exported to CSV";
    }
}

class PdfExportDecorator extends ReportDecorator {
    public PdfExportDecorator(IReport report) {
        super(report);
    }

    public String generate() {
        return super.generate() + ", Exported to PDF";
    }
}

public class ReportSystem {
    public static void main(String[] args) {
        IReport salesReport = new SalesReport();
        IReport filteredReport = new DateFilterDecorator(salesReport, "2023-01-01", "2023-12-31");
        IReport sortedReport = new SortingDecorator(filteredReport, "Date");
        IReport csvReport = new CsvExportDecorator(sortedReport);

        System.out.println(csvReport.generate());

        IReport userReport = new UserReport();
        IReport pdfReport = new PdfExportDecorator(userReport);
        System.out.println(pdfReport.generate());
    }
}

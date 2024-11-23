interface IInternalDeliveryService {
    void deliverOrder(String orderId);
    void getDeliveryStatus(String orderId);
}

class InternalDeliveryService implements IInternalDeliveryService {
    public void deliverOrder(String orderId) {
        System.out.println("Internal delivery service: Delivering order " + orderId);
    }

    public void getDeliveryStatus(String orderId) {
        System.out.println("Internal delivery service: Delivery status for " + orderId + " is 'In Transit'");
    }
}

class ExternalLogisticsServiceA {
    public void shipItem(int itemId) {
        System.out.println("External Logistics A: Shipping item with ID " + itemId);
    }

    public void trackShipment(int shipmentId) {
        System.out.println("External Logistics A: Tracking shipment with ID " + shipmentId);
    }
}

class ExternalLogisticsServiceB {
    public void sendPackage(String packageInfo) {
        System.out.println("External Logistics B: Sending package " + packageInfo);
    }

    public void checkPackageStatus(String trackingCode) {
        System.out.println("External Logistics B: Checking package status for " + trackingCode);
    }
}

class LogisticsAdapterA implements IInternalDeliveryService {
    private ExternalLogisticsServiceA externalService;

    public LogisticsAdapterA(ExternalLogisticsServiceA externalService) {
        this.externalService = externalService;
    }

    public void deliverOrder(String orderId) {
        externalService.shipItem(Integer.parseInt(orderId));
    }

    public void getDeliveryStatus(String orderId) {
        externalService.trackShipment(Integer.parseInt(orderId));
    }
}

class LogisticsAdapterB implements IInternalDeliveryService {
    private ExternalLogisticsServiceB externalService;

    public LogisticsAdapterB(ExternalLogisticsServiceB externalService) {
        this.externalService = externalService;
    }

    public void deliverOrder(String orderId) {
        externalService.sendPackage(orderId);
    }

    public void getDeliveryStatus(String orderId) {
        externalService.checkPackageStatus(orderId);
    }
}

class DeliveryServiceFactory {
    public static IInternalDeliveryService getDeliveryService(String type) {
        if (type.equalsIgnoreCase("internal")) {
            return new InternalDeliveryService();
        } else if (type.equalsIgnoreCase("externalA")) {
            return new LogisticsAdapterA(new ExternalLogisticsServiceA());
        } else if (type.equalsIgnoreCase("externalB")) {
            return new LogisticsAdapterB(new ExternalLogisticsServiceB());
        }
        throw new IllegalArgumentException("Unknown delivery service type");
    }
}

public class LogisticsSystem {
    public static void main(String[] args) {
        IInternalDeliveryService internalService = DeliveryServiceFactory.getDeliveryService("internal");
        internalService.deliverOrder("123");
        internalService.getDeliveryStatus("123");

        IInternalDeliveryService externalServiceA = DeliveryServiceFactory.getDeliveryService("externalA");
        externalServiceA.deliverOrder("456");
        externalServiceA.getDeliveryStatus("456");

        IInternalDeliveryService externalServiceB = DeliveryServiceFactory.getDeliveryService("externalB");
        externalServiceB.deliverOrder("789");
        externalServiceB.getDeliveryStatus("789");
    }
}

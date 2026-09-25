import SwiftUI
import Shared

@main
struct iOSApp: App {
    var body: some Scene {
        WindowGroup {
            ContentView()
                //an invoice shared with or opened in this app, see CFBundleDocumentTypes in Info.plist
                .onOpenURL { url in
                    guard let localURL = copyIntoApp(url) else { return }
                    MainViewControllerKt.openExternalFile(url: localURL.absoluteString)
                }
        }
    }

    /**
     * "Open with" hands over the file where it is, in Files or iCloud Drive, readable only while
     * the app holds access to it. The copy in the app's own directory stays readable for as long
     * as the SDK needs it.
     */
    private func copyIntoApp(_ url: URL) -> URL? {
        let accessed = url.startAccessingSecurityScopedResource()
        defer { if accessed { url.stopAccessingSecurityScopedResource() } }

        let fileManager = FileManager.default
        let target = fileManager.temporaryDirectory.appendingPathComponent(url.lastPathComponent)
        do {
            if fileManager.fileExists(atPath: target.path) {
                try fileManager.removeItem(at: target)
            }
            try fileManager.copyItem(at: url, to: target)
            return target
        } catch {
            return nil
        }
    }
}

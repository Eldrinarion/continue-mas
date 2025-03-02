package com.github.continuedev.continueintellijextension.editor

import com.github.continuedev.continueintellijextension.services.TelemetryService
import com.github.continuedev.continueintellijextension.utils.getMachineUniqueID
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.editor.Editor

@Service(Service.Level.PROJECT)
class DiffStreamService {
    private val handlers = mutableMapOf<Editor, DiffStreamHandler>()
    private val telemetryService = service<TelemetryService>()
    init {
        this.telemetryService.setup(getMachineUniqueID())
    }

    fun register(handler: DiffStreamHandler, editor: Editor) {
        if (handlers.containsKey(editor)) {
            handlers[editor]?.rejectAll()
        }
        handlers[editor] = handler
        println("Registered handler for editor")
    }

    fun reject(editor: Editor) {
        handlers[editor]?.rejectAll()
        handlers.remove(editor)
    }

    fun accept(editor: Editor) {
        handlers[editor]?.acceptAll()
        handlers.remove(editor)
    }
}
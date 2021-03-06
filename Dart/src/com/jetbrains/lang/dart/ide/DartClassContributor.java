package com.jetbrains.lang.dart.ide;

import com.intellij.navigation.ChooseByNameContributorEx;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.util.Comparing;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.Function;
import com.intellij.util.Processor;
import com.intellij.util.containers.JBIterable;
import com.intellij.util.indexing.FileBasedIndex;
import com.intellij.util.indexing.FindSymbolParameters;
import com.intellij.util.indexing.ID;
import com.intellij.util.indexing.IdFilter;
import com.jetbrains.lang.dart.psi.DartComponent;
import com.jetbrains.lang.dart.util.DartResolveUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;

import static com.jetbrains.lang.dart.ide.index.DartClassIndex.DART_CLASS_INDEX;

/**
 * @author: Fedor.Korotkov
 */
public class DartClassContributor implements ChooseByNameContributorEx {
  @Override
  public void processNames(@NotNull Processor<String> processor, @NotNull GlobalSearchScope scope, @Nullable IdFilter filter) {
    FileBasedIndex.getInstance().processAllKeys(DART_CLASS_INDEX, processor, scope, filter);
  }

  @Override
  public void processElementsWithName(@NotNull String name,
                                      @NotNull Processor<NavigationItem> processor,
                                      @NotNull FindSymbolParameters parameters) {
    doProcessElements(DART_CLASS_INDEX, DartResolveUtil::getClassDeclarations, name, processor, parameters);
  }

  static void doProcessElements(@NotNull ID<String, Void> index,
                                @NotNull Function<PsiElement, Iterable<? extends DartComponent>> componentGetter,
                                @NotNull String name,
                                @NotNull Processor<NavigationItem> processor,
                                @NotNull FindSymbolParameters parameters) {
    PsiManager psiManager = PsiManager.getInstance(parameters.getProject());
    FileBasedIndex.getInstance().getFilesWithKey(
      index, Collections.singleton(name), file ->
        JBIterable.of(psiManager.findFile(file))
          .flatMap(DartResolveUtil::findDartRoots)
          .flatMap(componentGetter)
          .filter(o -> Comparing.equal(name, o.getName()))
          .filterMap(DartComponent::getComponentName)
          .processEach(processor), parameters.getSearchScope());
  }
}
